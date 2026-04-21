package t1.llm.api.openai.chat.completions;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.exceptions.TaskNotImplementedException;
import commons.model.Message;
import commons.model.Role;
import t1.llm.api.openai.BaseOpenAiClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        // TODO:
        // https://platform.openai.com/docs/api-reference/chat/create
        // - Build JSON body using buildRequestBody(messages, false)
        // - Build HttpRequest using buildRequest(body)
        // - Send with HttpClient using BodyHandlers.ofString()
        // - Throw RuntimeException if response status is not 200
        // - Parse JSON with ObjectMapper; extract content at /choices/0/message/content
        // - Print content to stdout
        // - Return new Message(Role.ASSISTANT, content)
        // - Wrap all checked exceptions in RuntimeException
        throw new TaskNotImplementedException();
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        // TODO:
        // https://platform.openai.com/docs/api-reference/chat/create (Streaming tab)
        // - Build JSON body using buildRequestBody(messages, true)
        // - Build HttpRequest using buildRequest(body)
        // - Send with HttpClient using BodyHandlers.ofLines() to get a Stream<String>
        // - Filter lines starting with "data: "; strip the prefix
        // - Stop processing when the sentinel "[DONE]" is encountered (use takeWhile)
        // - For each remaining JSON line, parse with ObjectMapper; extract /choices/0/delta/content
        // - Print each non-empty delta to stdout; accumulate in a StringBuilder
        // - Print a newline after the stream ends
        // - Return new Message(Role.ASSISTANT, accumulated content)
        // - Wrap all checked exceptions in RuntimeException
        throw new TaskNotImplementedException();
    }

    private HttpRequest buildRequest(String body) {
        // TODO:
        // - Build an HttpRequest.Builder with URI from endpoint
        // - Add "Authorization" header using apiKey (already contains "Bearer " prefix)
        // - Add "Content-Type: application/json" header
        // - Set POST body with HttpRequest.BodyPublishers.ofString(body)
        // - Build and return the HttpRequest
        throw new TaskNotImplementedException();
    }

    private String buildRequestBody(List<Message> messages, boolean stream) {
        // TODO:
        // - Create a messages list; prepend the system message as Map("role"->"system", "content"->systemPrompt)
        // - Convert each Message to a map via Message.toMap() and append
        // - Build a body LinkedHashMap with "model" and "messages" keys
        // - If stream is true, add "stream": true
        // - Serialize to JSON string with ObjectMapper and return
        // - Wrap checked exceptions in RuntimeException
        throw new TaskNotImplementedException();
    }
}
