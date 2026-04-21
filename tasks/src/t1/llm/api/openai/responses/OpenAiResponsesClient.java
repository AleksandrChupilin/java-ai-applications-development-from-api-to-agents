package t1.llm.api.openai.responses;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.ResponsesModel;
import commons.exceptions.TaskNotImplementedException;
import commons.model.Message;
import commons.model.Role;
import t1.llm.api.openai.BaseOpenAiClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OpenAI Responses API client using the official OpenAI Java SDK.
 * <p>
 * The Responses API differs from Chat Completions: it uses {@code instructions} for the system
 * prompt and {@code input} for the conversation history. Compare with
 * {@link CustomOpenAiResponsesClient} which demonstrates the raw HTTP layer.
 */
public class OpenAiResponsesClient extends BaseOpenAiClient {

    private OpenAIClient client;

    public OpenAiResponsesClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
        // TODO:
        // - Build an OpenAIClient using OpenAIOkHttpClient.builder(), set apiKey, and call build()
        // - Assign the result to this.client
    }

    @Override
    public Message response(List<Message> messages) {
        // TODO:
        // - Build ResponseCreateParams using buildParams(messages)
        // - Call client.responses().create(params)
        // - Find the output item where isMessage() is true; find the content part where isOutputText() is true
        // - Extract the text string via asOutputText().text()
        // - Print content to stdout
        // - Return new Message(Role.ASSISTANT, content)
        throw new TaskNotImplementedException();
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        // TODO:
        // - Build ResponseCreateParams using buildParams(messages)
        // - Open a streaming call via client.responses().createStreaming(params) (try-with-resources)
        // - Filter events where isOutputTextDelta() is true; extract delta text via asOutputTextDelta().delta()
        // - Print each delta to stdout; accumulate in a StringBuilder
        // - Print a newline after the stream ends
        // - Return new Message(Role.ASSISTANT, accumulated content)
        throw new TaskNotImplementedException();
    }

    private ResponseCreateParams buildParams(List<Message> messages) {
        // TODO:
        // - For each Message, build a ResponseInputItem via ResponseInputItem.ofEasyInputMessage()
        //   using EasyInputMessage.builder() with role (EasyInputMessage.Role.of(role.getValue())) and content
        // - Build ResponseCreateParams with:
        //   - model: ResponsesModel.ofString(modelName)
        //   - instructions: systemPrompt
        //   - inputOfResponse: the list of ResponseInputItems
        // - Build and return the params
        throw new TaskNotImplementedException();
    }
}
