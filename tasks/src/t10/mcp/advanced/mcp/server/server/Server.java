package t10.mcp.advanced.mcp.server.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.exceptions.TaskNotImplementedException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import t10.mcp.advanced.mcp.server.server.models.McpRequest;
import t10.mcp.advanced.mcp.server.server.models.McpResponse;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class Server {

    private static final String SESSION_ID_HEADER = "Mcp-Session-Id";

    private final UmsMcpServer mcpServer;
    private final ObjectMapper objectMapper;

    public Server(UmsMcpServer mcpServer) {
        this.mcpServer = mcpServer;
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping(value = "/mcp", consumes = "application/json")
    public void handleMcp(
            @RequestBody McpRequest request,
            @RequestHeader(value = "Accept", required = false) String accept,
            @RequestHeader(value = SESSION_ID_HEADER, required = false) String sessionId,
            HttpServletResponse httpResponse
    ) throws IOException {
        //TODO:
        // - validate Accept header (must contain JSON and SSE)
        // - handle "initialize" method: call mcpServer.handleInitialize() and writeSse()
        // - validate Mcp-Session-Id header and retrieve session from mcpServer
        // - handle "notifications/initialized": set session ready and return 202
        // - check if session is ready for operation
        // - handle "tools/list" and "tools/call" methods by delegating to mcpServer
        // - write MCP response to SSE stream
    }

    private void writeSse(HttpServletResponse httpResponse, McpResponse response, String sessionId)
            throws IOException {
        //TODO:
        // - set response headers for SSE (Content-Type, Cache-Control, Connection)
        // - write MCP response prefixed with "data: "
        // - write "[DONE]" marker to terminate stream
    }

    private void writeError(HttpServletResponse httpResponse, int status, McpResponse error)
            throws IOException {
        //TODO:
        // - set response headers for JSON and status code
        // - write serialized error response
    }

    private boolean isValidAcceptHeader(String accept) {
        //TODO:
        // - check if accept header contains both application/json and text/event-stream
        throw new TaskNotImplementedException();
    }
}
