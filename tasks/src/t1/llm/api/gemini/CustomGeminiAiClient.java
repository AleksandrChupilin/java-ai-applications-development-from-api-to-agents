package t1.llm.api.gemini;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import t1.llm.api.AiClient;
import commons.model.Message;
import commons.model.Role;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static commons.Constants.GEMINI_ENDPOINT;

/**
 * Google Gemini client using raw HTTP — no official stable Java SDK available.
 * <p>
 * Key differences from OpenAI/Anthropic:
 * <ul>
 *   <li>Auth header is {@code x-goog-api-key} (not Authorization/x-api-key)</li>
 *   <li>System prompt goes in {@code system_instruction.parts[].text}</li>
 *   <li>The role for AI messages is {@code "model"}, not {@code "assistant"}</li>
 *   <li>Non-streaming URL: {@code {endpoint}/{model}:generateContent}</li>
 *   <li>Streaming URL: {@code {endpoint}/{model}:streamGenerateContent?alt=sse}</li>
 *   <li>Response path: {@code candidates[0].content.parts[*].text}</li>
 * </ul>
 */
public class CustomGeminiAiClient extends AiClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final HttpClient http = HttpClient.newHttpClient();

    public CustomGeminiAiClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
    }

    @Override
    public Message response(List<Message> messages) {
        String url = "%s/%s:generateContent".formatted(GEMINI_ENDPOINT, modelName);
        String body = buildRequestBody(messages);
        HttpRequest request = buildRequest(url, body);
        try {
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Request failed with status code: " + response.statusCode() + ", body: " + response.body());
            }
            String content = extractPartsText(MAPPER.readTree(response.body()).path("candidates").get(0));
            System.out.println(content);
            return new Message(Role.ASSISTANT, content);
        } catch (Exception e) {
            throw new RuntimeException("Error during API request: " + e.getMessage(), e);
        }
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        // https://ai.google.dev/api/generate-content#method:-models.streamgeneratecontent
        String url = "%s/%s:streamGenerateContent?alt=sse".formatted(GEMINI_ENDPOINT, modelName);
        String body = buildRequestBody(messages);
        HttpRequest request = buildRequest(url, body);
        StringBuilder accumulatedContent = new StringBuilder();
        try {
            HttpResponse<Stream<String>> response = http.send(request, HttpResponse.BodyHandlers.ofLines());
            response.body()
                    .filter(line -> line.startsWith("data: "))
                    .map(line -> line.substring("data: ".length()).strip())
                    .forEach(data -> {
                        try {
                            String chunkText = extractPartsText(MAPPER.readTree(data).path("candidates").get(0));
                            if (StringUtils.hasText(chunkText)) {
                                System.out.print(chunkText);
                                accumulatedContent.append(chunkText);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse Gemini stream chunk", e);
                        }
                    });
            System.out.println();
            return new Message(Role.ASSISTANT, accumulatedContent.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error during API request: " + e.getMessage(), e);
        }
    }

    private HttpRequest buildRequest(String url, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    private String buildRequestBody(List<Message> messages) {
        Map<String, List<Map<String, String>>> systemInstruction = Map.of("parts", List.of(Map.of("text", systemPrompt)));
        List<Map<String, Object>> contents = messages.stream()
                .map(message -> Map.of(
                        "role",  toGeminiRole(message.role()),
                        "parts", List.of(Map.of("text", message.content()))
                ))
                .toList();
        Map<String, Object> body = new LinkedHashMap<>(3);
        body.put("system_instruction", systemInstruction);
        body.put("contents", contents);
        body.put("generationConfig", Map.of("maxOutputTokens", 1024));
        try {
            return MAPPER.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Gemini request body", e);
        }
    }

    private String extractPartsText(JsonNode candidate) {
        StringBuilder content = new StringBuilder();
        JsonNode parts = candidate.path("content").path("parts");
        if (parts.isArray()) {
            for (JsonNode part : parts) {
                content.append(part.path("text").asText(""));
            }
        }
        return content.toString();
    }

    private String toGeminiRole(Role role) {
        return Role.ASSISTANT.equals(role) ? "model" : role.getValue();
    }
}
