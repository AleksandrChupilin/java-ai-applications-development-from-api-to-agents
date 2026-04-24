package t8.agent.task.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commons.exceptions.TaskNotImplementedException;

import java.util.Map;

public abstract class BaseTool {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public abstract String execute(Map<String, Object> arguments);

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getInputSchema();

    public String getOpenAiSchema() {
        //TODO:
        // https://developers.openai.com/api/docs/guides/function-calling#defining-functions
        // - Build a "function" ObjectNode with getName(), getDescription(), and getInputSchema() as "parameters"
        // - Wrap it in a root ObjectNode with "type": "function" and "function": <function node>
        // - Return JSON string via MAPPER.writeValueAsString()
        throw new TaskNotImplementedException();
    }

    public String getAnthropicSchema() {
        //TODO:
        // https://platform.claude.com/docs/en/api/messages/create#create.tools
        // - Build ObjectNode with getName() as "name", getDescription() as "description",
        //   and getInputSchema() parsed as "input_schema"
        // - Return JSON string via MAPPER.writeValueAsString()
        throw new TaskNotImplementedException();
    }
}
