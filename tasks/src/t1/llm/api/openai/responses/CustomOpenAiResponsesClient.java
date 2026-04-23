package t1.llm.api.openai.responses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.exceptions.TaskNotImplementedException;
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
        //TODO:
        // https://platform.openai.com/docs/api-reference/responses/create
        // - Build JSON body using buildRequestBody(messages, false)
        // - Build HttpRequest using buildRequest(body)
        // - Send with HttpClient using BodyHandlers.ofString()
        // - Throw RuntimeException if response status is not 200
        // - Parse JSON with ObjectMapper; extract output text using extractOutputText()
        // - Print content to stdout
        // - Return new Message(Role.ASSISTANT, content)
        // - Wrap all checked exceptions in RuntimeException
        throw new TaskNotImplementedException();
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        //TODO:
        // https://platform.openai.com/docs/api-reference/responses/create (Streaming tab)
        // - Build JSON body using buildRequestBody(messages, true)
        // - Build HttpRequest using buildRequest(body)
        // - Send with HttpClient using BodyHandlers.ofLines()
        // - Iterate over lines, tracking the current SSE event type (lines starting with "event: ")
        // - For "data: " lines where current event type is "response.output_text.delta":
        //   parse JSON with ObjectMapper and extract the "delta" field text
        // - Print each non-empty delta to stdout; accumulate in a StringBuilder
        // - Reset current event tracker on empty lines
        // - Print a newline after the stream ends
        // - Return new Message(Role.ASSISTANT, accumulated content)
        // - Wrap all checked exceptions in RuntimeException
        throw new TaskNotImplementedException();
    }

    private HttpRequest buildRequest(String body) {
        //TODO:
        // - Build an HttpRequest.Builder with URI from endpoint
        // - Add "Authorization" header using apiKey (already contains "Bearer " prefix)
        // - Add "Content-Type: application/json" header
        // - Set POST body with HttpRequest.BodyPublishers.ofString(body)
        // - Build and return the HttpRequest
        throw new TaskNotImplementedException();
    }

    private String buildRequestBody(List<Message> messages, boolean stream) {
        //TODO:
        // - Convert each Message to a map via Message.toMap() and collect to a list
        // - Build a body LinkedHashMap with "model", "instructions" (systemPrompt), and "input" (messages list)
        // - If stream is true, add "stream": true
        // - Serialize to JSON string with ObjectMapper and return
        // - Wrap checked exceptions in RuntimeException
        throw new TaskNotImplementedException();
    }

    private String extractOutputText(JsonNode root) {
        //TODO:
        // - Iterate over the "output" array in the root JsonNode
        // - Find items where type field equals "message"
        // - Within each such item, iterate the "content" array and find parts where type equals "output_text"
        // - Return the "text" field value from the first matching part
        // - Throw RuntimeException if no output text is found
        throw new TaskNotImplementedException();
    }
}
