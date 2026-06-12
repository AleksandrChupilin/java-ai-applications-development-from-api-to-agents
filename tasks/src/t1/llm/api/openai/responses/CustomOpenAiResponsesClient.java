package t1.llm.api.openai.responses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.model.Message;
import commons.model.Role;
import t1.llm.api.openai.BaseOpenAiClient;

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
 * OpenAI Responses API client using raw HTTP — no SDK.
 * <p>
 * The Responses API uses an event-based SSE format different from Chat Completions:
 * each SSE frame consists of an "event: &lt;type&gt;" line followed by "data: &lt;json&gt;".
 * Only frames with type "response.output_text.delta" carry text content.
 */
public class CustomOpenAiResponsesClient extends BaseOpenAiClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final HttpClient http = HttpClient.newHttpClient();

    public CustomOpenAiResponsesClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
    }

    @Override
    public Message response(List<Message> messages) {
        // https://platform.openai.com/docs/api-reference/responses/create
        String body = buildRequestBody(messages, false);
        HttpRequest request = buildRequest(body);
        try {
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "Request failed with status code: " + response.statusCode() + ", body: " + response.body()
                );
            }
            String content = extractOutputText(MAPPER.readTree(response.body()));
            System.out.println(content);
            return new Message(Role.ASSISTANT, content);
        } catch (Exception e) {
            throw new RuntimeException("Error during API request: " + e.getMessage(), e);
        }
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        // https://platform.openai.com/docs/api-reference/responses/create (Streaming tab)
        String body = buildRequestBody(messages, true);
        HttpRequest request = buildRequest(body);
        StringBuilder accumulatedContent = new StringBuilder();
        String currentEvent = null;

        try {
            HttpResponse<Stream<String>> response = http.send(request, HttpResponse.BodyHandlers.ofLines());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Request failed with status code: " + response.statusCode());
            }

            Iterator<String> iterator = response.body().iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();

                if (line.isEmpty()) {
                    currentEvent = null;
                    continue;
                }

                if (line.startsWith("event: ")) {
                    currentEvent = line.substring("event: ".length()).strip();
                    continue;
                }

                if (!line.startsWith("data: ") || !"response.output_text.delta".equals(currentEvent)) {
                    continue;
                }

                String data = line.substring("data: ".length());
                if ("[DONE]".equals(data)) {
                    break;
                }

                String delta = MAPPER.readTree(data).path("delta").asText("");
                if (!delta.isBlank()) {
                    System.out.print(delta);
                    accumulatedContent.append(delta);
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
                .header("Authorization", apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    private String buildRequestBody(List<Message> messages, boolean stream) {
        List<Map<String, Object>> input = messages.stream()
                .map(Message::toMap)
                .toList();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model", modelName);
        body.put("instructions", systemPrompt);
        body.put("input", input);
        if (stream) {
            body.put("stream", true);
        }

        try {
            return MAPPER.writeValueAsString(body);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize Responses request body", e);
        }
    }

    private String extractOutputText(JsonNode root) {
        JsonNode output = root.path("output");
        if (!output.isArray()) {
            throw new RuntimeException("No output array in Responses API response");
        }

        for (JsonNode item : output) {
            if (!"message".equals(item.path("type").asText())) {
                continue;
            }

            JsonNode content = item.path("content");
            if (!content.isArray()) {
                continue;
            }

            for (JsonNode part : content) {
                if (!"output_text".equals(part.path("type").asText())) {
                    continue;
                }
                String text = part.path("text").asText(null);
                if (text != null) {
                    return text;
                }
            }
        }

        throw new RuntimeException("No output text found in Responses API response");
    }
}
