package t9.mcp.fundamentals.agent;

import commons.Constants;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;
import io.modelcontextprotocol.spec.McpSchema;
import t9.mcp.fundamentals.agent.mcp.client.BaseClient;
import t9.mcp.fundamentals.agent.mcp.client.HttpClient;
import t9.mcp.fundamentals.agent.mcp.client.StdioClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    // Classpath of the compiled project for launching the STDIO server as a subprocess
    private static final String STDIO_SERVER_CLASS = "t9.mcp.fundamentals.mcp.server.StdioServerApp";

    public static void main(String[] args) throws Exception {
        // Switch active client by commenting/uncommenting:

        String javaClasspath = System.getProperty("java.class.path");

        // --- HTTP client (start HttpServer.java first): ---
        try (BaseClient mcpClient = new HttpClient("http://localhost:8005/mcp")) {
            runAgent(mcpClient);
        }

//        try (BaseClient mcpClient = new StdioClient(
//                null,
//                "java",
//                List.of("-cp", javaClasspath, STDIO_SERVER_CLASS),
//                null
//        )) {
//            runAgent(mcpClient);
//        }

        // --- Docker STDIO client: ---
//        try (BaseClient mcpClient = new StdioClient("mcp/duckduckgo:latest", null, null, null)) {
//            runAgent(mcpClient);
//        }
    }

    private static void runAgent(BaseClient mcpClient) {
        //TODO:
        // - call mcpClient.connect()
        // - list and print resources via mcpClient.getResources() (each r.uri() + r.description())
        // - list and print tools via mcpClient.getTools() (each function name + description)
        // - initialize messages list; add system message from Prompts.SYSTEM_PROMPT
        // - list prompts via mcpClient.getPrompts(); for each get content and add as user message
        //   with "## Prompt provided by MCP server:\n" + description + "\n" + content prefix
        // - create Agent with OPENAI_API_KEY, GPT model, tools, mcpClient
        // - run interactive chat loop: read input, add as user message, call agent.getCompletion(),
        //   add response; exit on "exit"
    }
}
