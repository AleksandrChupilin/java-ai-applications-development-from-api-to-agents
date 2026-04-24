package t8.agent.task.agents;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Constants;
import commons.exceptions.TaskNotImplementedException;
import commons.model.Message;
import commons.model.Role;
import t8.agent.task.tools.BaseTool;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OpenAIBasedAgent extends BaseAgent {

    private String endpoint;
    private List<JsonNode> toolsSchemas;
    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public OpenAIBasedAgent(String model, String apiKey, List<BaseTool> tools, String systemPrompt) {
        super(model, apiKey, tools, systemPrompt);
        //TODO:
        // - Format `apiKey` as Bearer token and reassign `this.apiKey` (e.g. "Bearer " + apiKey)
        // - Set `endpoint` to `Constants.OPENAI_CHAT_COMPLETIONS_ENDPOINT`
        // - Initialize `httpClient` with `HttpClient.newHttpClient()` and `objectMapper` with `new ObjectMapper()`
        // - Build `toolsSchemas`: map each tool via `objectMapper.readTree(t.getOpenAiSchema())`
        // - Print `endpoint` and pretty-printed `toolsSchemas`
    }

    @Override
    @SuppressWarnings("unchecked")
    public Message getResponse(List<Message> messages, boolean printRequest) {
        //TODO:
        // - Build requestMessages: if systemPrompt set, prepend Message(Role.SYSTEM, systemPrompt).toMap()
        //   then map all messages via msg.toMap() — do NOT mutate the `messages` list itself
        // - Build requestData: model, requestMessages, toolsSchemas
        // - If `printRequest` — print endpoint and REQUEST payload (messages only)
        // - POST to endpoint with headers: Authorization (apiKey), Content-Type: application/json
        // - On HTTP 200: get choices[0], print RESPONSE
        //   - Extract content and tool_calls from choices[0]["message"]
        //   - Build ai_response: Message(ASSISTANT, content, null, null, toolCalls)
        //   - If finish_reason == "tool_calls": add ai_response to messages, call processToolCalls, recurse
        //   - Otherwise return ai_response; throw RuntimeException if choices is empty
        // - On non-200 — throw RuntimeException with status code and body
        throw new TaskNotImplementedException();
    }

    @SuppressWarnings("unchecked")
    private List<Message> processToolCalls(List<Map<String, Object>> toolCalls) throws IOException {
        //TODO:
        // - For each toolCall in toolCalls:
        //   - Extract toolCallId from toolCall.get("id")
        //   - Extract functionName from toolCall["function"]["name"]
        //   - Parse arguments from JSON string: objectMapper.readValue(toolCall["function"]["arguments"], Map.class)
        //   - Call `callTool(functionName, arguments)` and store the result
        //   - Add Message(Role.TOOL, result, toolCallId, functionName, null) to result list
        //   - Print function name and result
        // - Return the list of tool messages
        throw new TaskNotImplementedException();
    }
}
