package t1.llm.api.openai.chat.completions;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletionAssistantMessageParam;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import commons.exceptions.TaskNotImplementedException;
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

    private OpenAIClient client;

    public OpenAiChatCompletionsClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
        // TODO:
        // https://github.com/openai/openai-java
        // - Call super(endpoint, modelName, apiKey, systemPrompt)
        // - Build an OpenAIClient using OpenAIOkHttpClient.builder(), set apiKey, and call build()
        // - Assign the result to this.client
    }

    @Override
    public Message response(List<Message> messages) {
        // TODO:
        // - Build ChatCompletionCreateParams using buildParams(messages)
        // - Call client.chat().completions().create(params)
        // - Extract content string from choices[0].message.content() (throw if absent)
        // - Print content to stdout
        // - Return new Message(Role.ASSISTANT, content)
        throw new TaskNotImplementedException();
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        // TODO:
        // - Build ChatCompletionCreateParams using buildParams(messages)
        // - Open a streaming call via client.chat().completions().createStreaming(params) (try-with-resources)
        // - For each chunk, extract delta content from choices[0].delta.content()
        // - Print each non-empty delta token to stdout; accumulate in a StringBuilder
        // - Print a newline after the stream ends
        // - Return new Message(Role.ASSISTANT, accumulated content)
        throw new TaskNotImplementedException();
    }

    private ChatCompletionCreateParams buildParams(List<Message> messages) {
        // TODO:
        // - Create a ChatCompletionCreateParams builder; set model and add the system message (systemPrompt)
        // - Iterate messages: USER → addUserMessage(), ASSISTANT → addMessage() with ChatCompletionAssistantMessageParam
        // - Build and return the params
        throw new TaskNotImplementedException();
    }
}
