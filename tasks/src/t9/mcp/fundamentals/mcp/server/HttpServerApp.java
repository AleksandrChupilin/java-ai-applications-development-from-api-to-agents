package t9.mcp.fundamentals.mcp.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class HttpServerApp {

    public static void main(String[] args) {
        // https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html
        //TODO:
        // - create SpringApplication with HttpServerApp.class
        // - set default properties:
        //      - server.port=8005
        //      - spring.ai.mcp.server.name
        //      - version
        //      - protocol=STREAMABLE
        //      - logging.level.io.modelcontextprotocol=DEBUG
        //      - ogging.level.org.springaicommunity.mcp=DEBUG
        //      - logging.level.t9.mcp.fundamentals=INFO
        //      - spring.autoconfigure.exclude
        //      - org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
        //      - org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
        // - run the app
    }
}
