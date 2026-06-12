package t1.llm.api.anthropic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import t1.llm.api.AiClient;
import commons.model.Message;
import commons.model.Role;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Anthropic Claude client using raw HTTP — no SDK.
 * <p>
 * Key differences from OpenAI raw HTTP:
 * <ul>
 *   <li>Auth header is {@code x-api-key} (no "Bearer" prefix)</li>
 *   <li>Requires {@code anthropic-version: 2023-06-01} header</li>
 *   <li>System prompt is a top-level JSON field, not a message in the array</li>
 *   <li>{@code max_tokens} is required</li>
 *   <li>Streaming SSE: look for {@code content_block_delta} events with {@code text_delta} type;
 *       stop early on {@code message_stop}</li>
 * </ul>
 * This class extends {@link AiClient} directly — the API key is stored raw (no "Bearer" prefix).
 */
public class CustomAnthropicAiClient extends AiClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final HttpClient http = HttpClient.newHttpClient();

    public CustomAnthropicAiClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
    }

    @Override
    public Message response(List<Message> messages) {
        // https://docs.anthropic.com/en/api/messages
        String body = buildRequestBody(messages, false);
        HttpRequest request = buildRequest(body);
        try {
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Request failed with status code: " + response.statusCode() + ", body: " + response.body());
            }

            JsonNode root = MAPPER.readTree(response.body());
            StringBuilder content = new StringBuilder();
            JsonNode blocks = root.path("content");
            if (blocks.isArray()) {
                for (JsonNode block : blocks) {
                    if ("text".equals(block.path("type").asText())) {
                        content.append(block.path("text").asText(""));
                    }
                }
            }

            String text = content.toString();
            System.out.println(text);
            return new Message(Role.ASSISTANT, text);
        } catch (Exception e) {
            throw new RuntimeException("Error during API request: " + e.getMessage(), e);
        }
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        // https://docs.anthropic.com/en/api/messages-streaming
        String body = buildRequestBody(messages, true);
        HttpRequest request = buildRequest(body);
        StringBuilder accumulatedContent = new StringBuilder();

        try {
            HttpResponse<Stream<String>> response = http.send(request, HttpResponse.BodyHandlers.ofLines());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Request failed with status code: " + response.statusCode());
            }

            Iterator<String> iterator = response.body().iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (!line.startsWith("data: ")) {
                    continue;
                }

                String payload = line.substring("data: ".length()).strip();
                if (payload.isEmpty()) {
                    continue;
                }

                JsonNode event = MAPPER.readTree(payload);
                String eventType = event.path("type").asText("");
                if ("message_stop".equals(eventType)) {
                    break;
                }

                if (!"content_block_delta".equals(eventType)) {
                    continue;
                }

                JsonNode delta = event.path("delta");
                if (!"text_delta".equals(delta.path("type").asText(""))) {
                    continue;
                }

                String text = delta.path("text").asText("");
                if (!text.isBlank()) {
                    System.out.print(text);
                    accumulatedContent.append(text);
                }
            }

            System.out.println();
            return new Message(Role.ASSISTANT, accumulatedContent.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error during API request: " + e.getMessage(), e);
        }
    }

    private HttpRequest buildRequest(String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("x-api-key", apiKey)
                .header("Content-Type", "application/json")
                .header("anthropic-version", "2023-06-01")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    private String buildRequestBody(List<Message> messages, boolean stream) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model", modelName);
        body.put("system", systemPrompt);
        body.put("max_tokens", 1024);
        body.put("messages", messages.stream().map(Message::toMap).toList());
        if (stream) {
            body.put("stream", true);
        }

        try {
            return MAPPER.writeValueAsString(body);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize Anthropic request body", e);
        }
    }
}
