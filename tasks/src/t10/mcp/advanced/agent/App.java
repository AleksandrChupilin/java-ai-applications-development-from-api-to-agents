package t10.mcp.advanced.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;
import commons.Constants;
import t10.mcp.advanced.agent.clients.CustomMcpClient;
import t10.mcp.advanced.agent.clients.McpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        //TODO:
        // - initialize McpClient (or CustomMcpClient) with server URL
        // - fetch available tools from MCP client
        // - print tools details using objectMapper
        // - initialize Agent with API key, model, tools, and mcpClient
        // - implement chat loop: read user input, add to messages, get and print agent completion
    }
}

// Check if Arkadiy Dobkin present as a user, if not then search info about him in the web and add him
