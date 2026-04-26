package t9.mcp.fundamentals.mcp.server;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class StdioServerApp {

    public static void main(String[] args) {
        // https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html
        //TODO:
        // - create SpringApplication with StdioServerApp.class
        // - set banner mode OFF (Banner.Mode.OFF) and WebApplicationType.NONE
        // - set spring.ai.mcp.server.stdio=true (activates STDIO transport, not the protocol enum)
        // - set spring.ai.mcp.server.name, version
        // - disable all logging (logging.level.root=OFF) — stdout is exclusive to MCP JSON-RPC
        // - run the app
    }
}
