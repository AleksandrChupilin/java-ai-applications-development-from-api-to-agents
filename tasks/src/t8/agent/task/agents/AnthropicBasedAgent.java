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

public class AnthropicBasedAgent extends BaseAgent {

    private String endpoint;
    private List<JsonNode> toolsSchemas;
    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public AnthropicBasedAgent(String model, String apiKey, List<BaseTool> tools, String systemPrompt) {
        super(model, apiKey, tools, systemPrompt);
        //TODO:
        // - Set `endpoint` to `Constants.ANTHROPIC_ENDPOINT`
        // - Initialize `httpClient` with `HttpClient.newHttpClient()` and `objectMapper` with `new ObjectMapper()`
        // - Build `toolsSchemas`: map each tool via `objectMapper.readTree(t.getAnthropicSchema())`
        // - Print `endpoint` and pretty-printed `toolsSchemas`
    }

    @Override
    @SuppressWarnings("unchecked")
    public Message getResponse(List<Message> messages, boolean printRequest) {
        //TODO:
        // - Convert messages using `toAnthropicMessages(messages)`
        // - Build requestData: model, max_tokens=8096, anthropicMessages, toolsSchemas;
        //   add "system": systemPrompt if systemPrompt is set
        // - If `printRequest` — print endpoint and REQUEST payload (messages only)
        // - POST to endpoint with headers: x-api-key, anthropic-version: 2023-06-01, Content-Type: application/json
        // - On HTTP 200: parse content blocks and stop_reason; print RESPONSE
        //   - Extract text from first block where type == "text" (or null)
        //   - Extract tool_use blocks where type == "tool_use"
        //   - Build ai_response: Message(ASSISTANT, textContent, null, null, contentBlocks if tool_use else null)
        //   - If stop_reason == "tool_use": add ai_response to messages, call processToolCalls, recurse
        //   - Otherwise return ai_response
        // - On non-200 — throw RuntimeException with status code and body
        throw new TaskNotImplementedException();
    }

    private List<Map<String, Object>> toAnthropicMessages(List<Message> messages) {
        //TODO:
        // - Walk messages with index `i` using a while loop
        // - Role.TOOL: group consecutive TOOL messages into a single user message:
        //   {"role": "user", "content": [{"type": "tool_result", "tool_use_id": msg.toolCallId(), "content": msg.content()}, ...]}
        // - Role.ASSISTANT: use msg.toolCalls() as content if non-null, otherwise use msg.content()
        //   → {"role": "assistant", "content": <toolCalls or content>}
        // - Other roles: {"role": msg.role().getValue(), "content": msg.content()}
        // - Return the resulting list
        throw new TaskNotImplementedException();
    }

    @SuppressWarnings("unchecked")
    private List<Message> processToolCalls(List<Map<String, Object>> toolUseBlocks) {
        //TODO:
        // - For each block in toolUseBlocks:
        //   - Extract toolUseId from block.get("id")
        //   - Extract functionName from block.get("name")
        //   - Get arguments (Map) from block.get("input") — already a Map, no JSON parsing needed
        //   - Call `callTool(functionName, arguments)` and store the result
        //   - Add Message(Role.TOOL, result, toolUseId, functionName, null) to result list
        //   - Print function name and result
        // - Return the list of tool messages
        throw new TaskNotImplementedException();
    }
}
