package t13.task.agent.tools;

import commons.exceptions.TaskNotImplementedException;
import t13.task.agent.clients.BaseMcpClient;
import t13.task.agent.models.McpToolModel;

import java.util.Map;

public class McpTool extends BaseTool {

    private final BaseMcpClient client;
    private final McpToolModel model;

    public McpTool(BaseMcpClient client, McpToolModel model) {
        this.client = client;
        this.model = model;
    }

    @Override
    protected String executeInternal(Map<String, Object> arguments) {
        //TODO:
        // - Delegate to client.callTool() passing model.name() and arguments
        throw new TaskNotImplementedException();
    }

    @Override
    public String getName() {
        //TODO:
        // - Return model.name()
        throw new TaskNotImplementedException();
    }

    @Override
    public String getDescription() {
        //TODO:
        // - Return model.description()
        throw new TaskNotImplementedException();
    }

    @Override
    public Map<String, Object> getParameters() {
        //TODO:
        // - Return model.parameters()
        throw new TaskNotImplementedException();
    }
}
