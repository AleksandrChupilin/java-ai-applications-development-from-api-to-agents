package t10.mcp.advanced.mcp.server.server;

import commons.user.service.UserServiceClient;
import commons.exceptions.TaskNotImplementedException;
import org.springframework.stereotype.Service;
import t10.mcp.advanced.mcp.server.server.models.McpRequest;
import t10.mcp.advanced.mcp.server.server.models.McpResponse;
import t10.mcp.advanced.mcp.server.server.tools.BaseTool;
import t10.mcp.advanced.mcp.server.server.tools.users.CreateUserTool;
import t10.mcp.advanced.mcp.server.server.tools.users.DeleteUserTool;
import t10.mcp.advanced.mcp.server.server.tools.users.GetUserByIdTool;
import t10.mcp.advanced.mcp.server.server.tools.users.SearchUsersTool;
import t10.mcp.advanced.mcp.server.server.tools.users.UpdateUserTool;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UmsMcpServer {

    private static final String PROTOCOL_VERSION = "2025-11-25";

    private final Map<String, McpSession> sessions = new ConcurrentHashMap<>();
    private final Map<String, BaseTool> tools = new LinkedHashMap<>();

    public UmsMcpServer() {
        registerTools();
    }

    private void registerTools() {
        UserServiceClient client = new UserServiceClient();
        List.of(
                new GetUserByIdTool(client),
                new SearchUsersTool(client),
                new CreateUserTool(client),
                new UpdateUserTool(client),
                new DeleteUserTool(client)
        ).forEach(tool -> tools.put(tool.getName(), tool));
    }

    public McpSession getSession(String sessionId) {
        McpSession session = sessions.get(sessionId);
        if (session != null) {
            session.updateActivity();
        }
        return session;
    }

    public record InitResult(McpResponse response, String sessionId) {}

    public InitResult handleInitialize(McpRequest request) {
        //TODO:
        // - generate unique session ID and capabilities (tools, resources, prompts)
        // - build and return InitResult with protocolVersion, capabilities and serverInfo
        throw new TaskNotImplementedException();
    }

    public McpResponse handleToolsList(McpRequest request) {
        //TODO:
        // - collect all registered tools and convert them to MCP tool format using BaseTool.toMcpTool()
        // - return McpResponse with the list of tools
        throw new TaskNotImplementedException();
    }

    public McpResponse handleToolsCall(McpRequest request) {
        //TODO:
        // - validate request parameters and tool name
        // - find tool by name in the tools map
        // - execute tool with provided arguments
        // - wrap result text (or error) into MCP content structure and return McpResponse
        throw new TaskNotImplementedException();
    }
}
