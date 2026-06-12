package t1.llm.api.anthropic;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.core.http.StreamResponse;
import com.anthropic.models.messages.ContentBlock;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.RawContentBlockDelta;
import com.anthropic.models.messages.RawMessageStreamEvent;
import t1.llm.api.AiClient;
import commons.model.Message;
import commons.model.Role;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Anthropic Claude client using the official Anthropic Java SDK.
 * <p>
 * Claude's API differs from OpenAI: the system prompt is a separate {@code system} parameter,
 * not a message in the conversation. Max tokens must always be specified.
 * Compare with {@link CustomAnthropicAiClient} for the raw HTTP equivalent.
 */
public class AnthropicAiClient extends AiClient {

    private final AnthropicClient client;

    public AnthropicAiClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
        //TODO:
        // - https://github.com/anthropics/anthropic-sdk-java
        // - Build an AnthropicClient using AnthropicOkHttpClient.builder(), set apiKey, and call build()
        // - Assign the result to this.client
        this.client = AnthropicOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    @Override
    public Message response(List<Message> messages) {
        //TODO:
        // - Build MessageCreateParams using buildParams(messages)
        MessageCreateParams params = buildParams(messages);
        // - Call client.messages().create(params)
        com.anthropic.models.messages.Message response = client.messages().create(params);
        // - Filter the response content blocks for isText(); extract text via asText().text(); concatenate
        String content = response.content().stream()
                .filter(ContentBlock::isText)
                .map(block -> block.asText().text())
                .collect(Collectors.joining());
        // - Print content to stdout
        System.out.println(content);
        // - Return new Message(Role.ASSISTANT, content)
        return new commons.model.Message(Role.ASSISTANT, content);
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        //TODO:
        // - Build MessageCreateParams using buildParams(messages)
        MessageCreateParams params = buildParams(messages);
        // - Open a streaming call via client.messages().createStreaming(params) (try-with-resources)
        StringBuilder accumulatedContent = new StringBuilder();
        try (StreamResponse<RawMessageStreamEvent> stream =
                     client.messages().createStreaming(params)) {
        // - Filter events where isContentBlockDelta() is true
        // - From each event's delta, check isText(); extract text via asText().text()
        // - Print each non-empty text to stdout; accumulate in a StringBuilder
            stream.stream()
                    .filter(RawMessageStreamEvent::isContentBlockDelta)
                    .map(event -> event.asContentBlockDelta().delta())
                    .filter(RawContentBlockDelta::isText)
                    .map(delta -> delta.asText().text())
                    .filter(text -> !text.isBlank())
                    .forEach(text -> {
                        System.out.print(text);
                        accumulatedContent.append(text);
                    });
        }
        // - Print a newline after the stream ends
        System.out.println();
        // - Return new Message(Role.ASSISTANT, accumulated content)
        return new commons.model.Message(Role.ASSISTANT, accumulatedContent.toString());
    }

    private MessageCreateParams buildParams(List<Message> messages) {
        //TODO:
        // - Create a MessageCreateParams builder; set model, system (systemPrompt), and maxTokens (e.g. 1024)
        MessageCreateParams.Builder builder = MessageCreateParams.builder()
                .model(modelName)
                .system(systemPrompt)
                .maxTokens(1024);
        // - Iterate messages: USER → addUserMessage(), ASSISTANT → addAssistantMessage()
        for (Message message : messages) {
            if (message.role() == Role.USER) {
                builder.addUserMessage(message.content());
            } else if (message.role() == Role.ASSISTANT) {
                builder.addAssistantMessage(message.content());
            }
        }
        // - Build and return the params
        return builder.build();
    }
}
