package t1.llm.api.openai.chat.completions;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.model.Message;
import commons.model.Role;
import org.springframework.util.StringUtils;
import t1.llm.api.openai.BaseOpenAiClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * OpenAI Chat Completions client using raw HTTP — no SDK.
 * <p>
 * Shows what the SDK does under the hood: plain REST POST with JSON body,
 * and SSE line-by-line parsing for streaming.
 * The "data: [DONE]" sentinel marks the end of the stream.
 */
public class CustomOpenAiChatCompletionsClient extends BaseOpenAiClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final HttpClient http = HttpClient.newHttpClient();

    public CustomOpenAiChatCompletionsClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
    }

    @Override
    public Message response(List<Message> messages) {
        // https://platform.openai.com/docs/api-reference/chat/create
        String body = buildRequestBody(messages, false);
        System.out.println("[OpenAI request JSON] " + body);
        HttpRequest request = buildRequest(body);
        try {
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Request failed with status code: " + response.statusCode() + ", body: " + response.body());
            }
            System.out.println("[OpenAI response JSON] " + response.body());
            String content = MAPPER.readTree(response.body())
                    .path("choices").get(0)
                    .path("message")
                    .path("content")
                    .asText("");
            System.out.println(content);
            return new Message(Role.ASSISTANT, content);
        } catch (Exception e) {
            throw new RuntimeException("Error during API request: " + e.getMessage(), e);
        }
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        // https://platform.openai.com/docs/api-reference/chat/create (Streaming tab)
        String body = buildRequestBody(messages, true);
        System.out.println("[OpenAI stream request JSON] " + body);
        HttpRequest request = buildRequest(body);
        StringBuilder accumulatedContent = new StringBuilder();
        try {
            HttpResponse<Stream<String>> response = http.send(request, HttpResponse.BodyHandlers.ofLines());
            response.body()
                    .filter(line -> line.startsWith("data: "))
                    .map(line -> line.substring("data: ".length()).strip())
                    .takeWhile(data -> !"[DONE]".equals(data))
                    .forEach(data -> {
                        try {
                            System.out.println("[OpenAI stream response JSON] " + data);
                            String delta = MAPPER.readTree(data)
                                    .path("choices")
                                    .path(0)
                                    .path("delta")
                                    .path("content")
                                    .asText("");

                            if (StringUtils.hasText(delta)) {
                                System.out.print(delta);
                                accumulatedContent.append(delta);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse streaming chunk", e);
                        }
                    });
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
        List<Map<String, Object>> requestMessages = new ArrayList<>();
        requestMessages.add(Map.of("role", "system", "content", systemPrompt));
        messages.stream()
                .map(Message::toMap)
                .forEach(requestMessages::add);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model", modelName);
        body.put("messages", requestMessages);
        if (stream) {
            body.put("stream", true);
        }

        try {
            return MAPPER.writeValueAsString(body);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize OpenAI chat request body", e);
        }
    }
}
