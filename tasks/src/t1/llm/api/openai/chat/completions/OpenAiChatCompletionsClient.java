package t1.llm.api.openai.chat.completions;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.http.StreamResponse;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionAssistantMessageParam;
import com.openai.models.chat.completions.ChatCompletionChunk;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import commons.model.Message;
import commons.model.Role;
import t1.llm.api.openai.BaseOpenAiClient;

import java.util.List;

/**
 * OpenAI Chat Completions client using the official OpenAI Java SDK.
 * <p>
 * Demonstrates how the SDK abstracts HTTP and SSE details. Compare with
 * {@link CustomOpenAiChatCompletionsClient} which does the same via raw HTTP.
 */
public class OpenAiChatCompletionsClient extends BaseOpenAiClient {

    private final OpenAIClient client;

    public OpenAiChatCompletionsClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
        // https://github.com/openai/openai-java
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    @Override
    public Message response(List<Message> messages) {
        ChatCompletionCreateParams params = buildParams(messages);
        ChatCompletion chatCompletion = client.chat().completions().create(params);
        String content = chatCompletion.choices().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No choices returned"))
                .message()
                .content()
                .orElseThrow(() -> new RuntimeException("No content in response"));
        System.out.println(content);
        return new Message(Role.ASSISTANT, content);
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        ChatCompletionCreateParams params = buildParams(messages);
        StringBuilder accumulatedContent = new StringBuilder();
        try (StreamResponse<ChatCompletionChunk> streamResponse = client.chat().completions().createStreaming(params)) {
            streamResponse.stream().forEach(chunk -> {
                String deltaContent = chunk.choices().stream()
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No choices in chunk"))
                        .delta()
                        .content()
                        .orElse("");
                if (!deltaContent.isEmpty()) {
                    System.out.print(deltaContent);
                    accumulatedContent.append(deltaContent);
                }
            });
        }
        System.out.println();
        return new Message(Role.ASSISTANT, accumulatedContent.toString());
    }

    private ChatCompletionCreateParams buildParams(List<Message> messages) {
        ChatCompletionCreateParams.Builder builder = ChatCompletionCreateParams.builder()
                .model(modelName)
                .addSystemMessage(systemPrompt);

        messages.forEach(message -> {
            if (Role.ASSISTANT.equals(message.role())) {
                builder.addMessage(ChatCompletionAssistantMessageParam.builder()
                        .content(message.content())
                        .build());
            } else if (Role.USER.equals(message.role())) {
                builder.addUserMessage(message.content());

            }
        });
        return builder.build();
    }
}
