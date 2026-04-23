package t1.llm.api.anthropic;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.MessageCreateParams;
import commons.exceptions.TaskNotImplementedException;
import t1.llm.api.AiClient;
import commons.model.Message;
import commons.model.Role;

import java.util.List;

/**
 * Anthropic Claude client using the official Anthropic Java SDK.
 * <p>
 * Claude's API differs from OpenAI: the system prompt is a separate {@code system} parameter,
 * not a message in the conversation. Max tokens must always be specified.
 * Compare with {@link CustomAnthropicAiClient} for the raw HTTP equivalent.
 */
public class AnthropicAiClient extends AiClient {

    private AnthropicClient client;

    public AnthropicAiClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
        //TODO:
        // - https://github.com/anthropics/anthropic-sdk-java
        // - Build an AnthropicClient using AnthropicOkHttpClient.builder(), set apiKey, and call build()
        // - Assign the result to this.client
        throw new TaskNotImplementedException();
    }

    @Override
    public Message response(List<Message> messages) {
        //TODO:
        // - Build MessageCreateParams using buildParams(messages)
        // - Call client.messages().create(params)
        // - Filter the response content blocks for isText(); extract text via asText().text(); concatenate
        // - Print content to stdout
        // - Return new Message(Role.ASSISTANT, content)
        throw new TaskNotImplementedException();
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        //TODO:
        // - Build MessageCreateParams using buildParams(messages)
        // - Open a streaming call via client.messages().createStreaming(params) (try-with-resources)
        // - Filter events where isContentBlockDelta() is true
        // - From each event's delta, check isText(); extract text via asText().text()
        // - Print each non-empty text to stdout; accumulate in a StringBuilder
        // - Print a newline after the stream ends
        // - Return new Message(Role.ASSISTANT, accumulated content)
        throw new TaskNotImplementedException();
    }

    private MessageCreateParams buildParams(List<Message> messages) {
        //TODO:
        // - Create a MessageCreateParams builder; set model, system (systemPrompt), and maxTokens (e.g. 1024)
        // - Iterate messages: USER → addUserMessage(), ASSISTANT → addAssistantMessage()
        // - Build and return the params
        throw new TaskNotImplementedException();
    }
}
