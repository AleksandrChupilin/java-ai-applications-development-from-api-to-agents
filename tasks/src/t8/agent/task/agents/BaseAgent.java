package t8.agent.task.agents;

import commons.exceptions.TaskNotImplementedException;
import commons.model.Message;
import t8.agent.task.tools.BaseTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseAgent {

    protected String model;
    protected String apiKey;
    protected String systemPrompt;
    protected Map<String, BaseTool> toolsDict;

    public BaseAgent(String model, String apiKey, List<BaseTool> tools, String systemPrompt) {
        //TODO:
        // - Validate `apiKey` — throw IllegalArgumentException("API key cannot be null or empty") if null/blank
        // - Assign `model`, `apiKey`, `systemPrompt` fields
        // - Build `toolsDict` as HashMap: for each tool in `tools`, map `tool.getName()` → tool
    }

    /**
     * Send the conversation to the LLM and return its reply.
     * Tool calls are handled transparently via recursion until a plain text response is returned.
     * The messages list is mutated in-place to accumulate intermediate tool-call and tool-result messages.
     */
    public abstract Message getResponse(List<Message> messages, boolean printRequest);

    protected String callTool(String functionName, Map<String, Object> arguments) {
        //TODO:
        // - Look up tool by `functionName` in `toolsDict`
        // - If found — call `tool.execute(arguments)` and return the result
        // - If not found — return "Unknown function: " + functionName
        throw new TaskNotImplementedException();
    }
}
