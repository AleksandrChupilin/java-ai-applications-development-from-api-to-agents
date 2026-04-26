package t9.mcp.fundamentals.agent.mcp.client;

import commons.exceptions.TaskNotImplementedException;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.json.jackson.JacksonMcpJsonMapper;

import java.util.List;
import java.util.Map;

/**
 * MCP client that communicates via stdio with a locally spawned process.
 *
 * Supports two launch modes:
 *   1. Docker image  — pass dockerImage="mcp/duckduckgo:latest"
 *   2. Local command — pass command="java", args=["-cp", "...", "MainClass"]
 *
 * Usage:
 *   // Docker
 *   try (StdioClient client = new StdioClient("mcp/duckduckgo:latest", null, null, null)) { ... }
 *
 *   // Local Java process
 *   try (StdioClient client = new StdioClient(null, "java", List.of("-cp", "...", "StdioServer"), null)) { ... }
 */
public class StdioClient extends BaseClient {

    private final String dockerImage;
    private final String command;
    private final List<String> args;
    private final Map<String, String> env;

    public StdioClient(String dockerImage, String command, List<String> args, Map<String, String> env) {
        if (dockerImage == null && command == null) {
            throw new IllegalArgumentException("Provide either 'dockerImage' or 'command' to launch the MCP server.");
        }
        this.dockerImage = dockerImage;
        this.command = command;
        this.args = args != null ? args : List.of();
        this.env = env;
    }

    @Override
    public void connect() {
        //TODO:
        // - call buildServerParameters() to get ServerParameters
        // - print startupMessage()
        // - create StdioClientTransport(params, new JacksonMcpJsonMapper(objectMapper))
        // - create McpClient.sync(transport).build() and assign to mcpClient
        // - print "Initializing MCP session...", call mcpClient.initialize() then initToolCallbackProvider()
        // - print "MCP session initialized."
    }

    private ServerParameters buildServerParameters() {
        //TODO:
        // - if dockerImage != null: build ServerParameters with "docker" command and args ["run", "--rm", "-i", dockerImage]
        // - else: build ServerParameters with command and args.toArray(new String[0])
        // - include env in both cases via .env(env)
        throw new TaskNotImplementedException();
    }

    private String startupMessage() {
        //TODO:
        // - if dockerImage != null: return message with image name and docker ps inspection tip
        // - else: return message with command + joined args
        throw new TaskNotImplementedException();
    }
}
