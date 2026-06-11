package t1.llm.api.gemini;

import com.google.genai.Client;
import com.google.genai.ResponseStream;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import commons.model.Message;
import commons.model.Role;
import org.springframework.util.StringUtils;
import t1.llm.api.AiClient;

import java.util.List;
import java.util.Optional;

/**
 * Google Gemini client using the official Google GenAI Java SDK.
 * <p>
 * Key differences from OpenAI/Anthropic:
 * <ul>
 *   <li>System prompt goes in {@code GenerateContentConfig.systemInstruction}</li>
 *   <li>The role for AI messages is {@code "model"}, not {@code "assistant"}</li>
 *   <li>Streaming uses {@code ResponseStream<GenerateContentResponse>} which is {@code Iterable}</li>
 * </ul>
 * Compare with {@link CustomGeminiAiClient} for the raw HTTP equivalent.
 */
public class GeminiAiClient extends AiClient {

    private final Client client;

    public GeminiAiClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
        // https://github.com/googleapis/java-genai
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    @Override
    public Message response(List<Message> messages) {
        GenerateContentConfig config = buildConfig();
        List<Content> contents = buildContents(messages);
        GenerateContentResponse response = client.models.generateContent(modelName, contents, config);
        String content = Optional.ofNullable(response.text()).orElse("");
        System.out.println(content);
        return new Message(Role.ASSISTANT, content);
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        GenerateContentConfig config = buildConfig();
        List<Content> contents = buildContents(messages);
        StringBuilder accumulatedContent = new StringBuilder();
        try (ResponseStream<GenerateContentResponse> stream = client.models.generateContentStream(modelName, contents, config)) {
            for (GenerateContentResponse chunk : stream) {
                String chunkText = chunk.text();
                if (StringUtils.hasText(chunkText)) {
                    System.out.print(chunkText);
                    accumulatedContent.append(chunkText);
                }
            }
            System.out.println();
        }
        return new Message(Role.ASSISTANT, accumulatedContent.toString());
    }

    private GenerateContentConfig buildConfig() {
        return GenerateContentConfig.builder()
                .systemInstruction(Content.builder()
                        .parts(Part.fromText(systemPrompt))
                        .build())
                .maxOutputTokens(1024)
                .build();
    }

    private List<Content> buildContents(List<Message> messages) {
        return messages.stream()
                .map(message -> Content.builder()
                        .role(toGeminiRole(message.role()))
                        .parts(Part.fromText(message.content()))
                        .build())
                .toList();
    }

    private String toGeminiRole(Role role) {
        return Role.ASSISTANT.equals(role) ? "model" : role.getValue();
    }
}
