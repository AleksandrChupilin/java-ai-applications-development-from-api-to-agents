package t10.mcp.advanced.agent.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.exceptions.TaskNotImplementedException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Custom MCP client — raw HTTP implementation without external MCP libraries.
 * Uses string-based SSE parsing instead of Java Streams, making the protocol
 * handling explicit and easy to follow step by step.
 */
public class CustomMcpClient extends BaseMcpClient {

    private static final String SESSION_ID_HEADER = "Mcp-Session-Id";

    private final java.net.http.HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private String sessionId;

    private CustomMcpClient(String serverUrl) {
        super(serverUrl);
        this.httpClient = java.net.http.HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public static CustomMcpClient create(String serverUrl) {
        CustomMcpClient client = new CustomMcpClient(serverUrl);
        client.connect();
        return client;
    }

    private void connect() {
        //TODO:
        // - define 'initialize' parameters (protocolVersion, capabilities, clientInfo)
        // - call sendRequest() with "initialize" method and parameters
        // - send "notifications/initialized" notification using sendNotification()
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> sendRequest(String method, Map<String, Object> params)
            throws IOException, InterruptedException {
        //TODO:
        // - build JSON-RPC 2.0 request body with unique ID, method and params
        // - create HttpRequest with POST method, JSON content type and Accept headers (JSON and SSE)
        // - include Mcp-Session-Id header if sessionId is present and it is not an 'initialize' request
        // - send request using httpClient and BodyHandlers.ofString()
        // - extract and save Mcp-Session-Id from response headers
        // - if status is 202 (Accepted), return empty map
        // - parse SSE body using parseSseBody()
        // - deserialize JSON data to Map and check for "error" field
        throw new TaskNotImplementedException();
    }

    private String parseSseBody(String body) {
        //TODO:
        // - iterate over response body lines
        // - find line starting with "data: "
        // - extract data part, ensuring it is not "[DONE]" and not empty
        throw new TaskNotImplementedException();
    }

    private void sendNotification(String method) throws IOException, InterruptedException {
        //TODO:
        // - build JSON-RPC 2.0 notification body (no ID)
        // - create and send HttpRequest with POST method and mandatory headers
        // - refresh sessionId from response headers if present
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getTools() {
        //TODO:
        // - call sendRequest() with "tools/list" method
        // - extract tools from response result
        // - map MCP tools to OpenAI-compatible function format (name, description, parameters/inputSchema)
        throw new TaskNotImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String callTool(String name, Map<String, Object> arguments) {
        //TODO:
        // - call sendRequest() with "tools/call" method and parameters (name, arguments)
        // - extract and return text content from response result
        throw new TaskNotImplementedException();
    }
}
