package t1.llm.api.gemini;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.exceptions.TaskNotImplementedException;
import t1.llm.api.AiClient;
import commons.model.Message;
import commons.model.Role;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
        // TODO:
        // https://ai.google.dev/api/generate-content
        // - Build the non-streaming URL: endpoint + "/" + modelName + ":generateContent"
        // - Build JSON body using buildRequestBody(messages)
        // - Build HttpRequest using buildRequest(url, body)
        // - Send with HttpClient using BodyHandlers.ofString()
        // - Throw RuntimeException if response status is not 200
        // - Parse JSON with ObjectMapper; access candidates[0]; extract text using extractPartsText()
        // - Print content to stdout
        // - Return new Message(Role.ASSISTANT, content)
        // - Wrap all checked exceptions in RuntimeException
        throw new TaskNotImplementedException();
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        // TODO:
        // https://ai.google.dev/api/generate-content#method:-models.streamgeneratecontent
        // - Build the streaming URL: endpoint + "/" + modelName + ":streamGenerateContent?alt=sse"
        // - Build JSON body using buildRequestBody(messages)
        // - Build HttpRequest using buildRequest(url, body)
        // - Send with HttpClient using BodyHandlers.ofLines()
        // - Iterate lines starting with "data: "; parse JSON from each
        // - Access the "candidates" array; extract text from candidates[0] using extractPartsText()
        // - Print each non-empty text to stdout; accumulate in a StringBuilder
        // - Print a newline after the stream ends
        // - Return new Message(Role.ASSISTANT, accumulated content)
        // - Wrap all checked exceptions in RuntimeException
        throw new TaskNotImplementedException();
    }

    private HttpRequest buildRequest(String url, String body) {
        // TODO:
        // - Build an HttpRequest.Builder with URI from the given url string
        // - Add "Content-Type: application/json" header
        // - Add "x-goog-api-key" header with apiKey (Gemini uses this instead of Authorization)
        // - Set POST body with HttpRequest.BodyPublishers.ofString(body)
        // - Build and return the HttpRequest
        throw new TaskNotImplementedException();
    }

    private String buildRequestBody(List<Message> messages) {
        // TODO:
        // - Build "system_instruction" as a Map containing "parts": list of {"text": systemPrompt}
        // - Build "contents" list: for each Message, create a Map with
        //     "role": toGeminiRole(m.role()) and "parts": list of {"text": m.content()}
        // - Build a body LinkedHashMap with "system_instruction", "contents",
        //   and "generationConfig" containing "maxOutputTokens"
        // - Serialize to JSON string with ObjectMapper and return
        // - Wrap checked exceptions in RuntimeException
        throw new TaskNotImplementedException();
    }

    private String extractPartsText(JsonNode candidate) {
        // TODO:
        // - Iterate over the candidate's content.parts array
        // - For each part, extract the "text" field value and append to a StringBuilder
        // - Return the concatenated string
        throw new TaskNotImplementedException();
    }

    private String toGeminiRole(Role role) {
        // TODO:
        // - Return "model" if the role is Role.ASSISTANT (Gemini uses "model" not "assistant")
        // - Otherwise return role.getValue()
        throw new TaskNotImplementedException();
    }
}
