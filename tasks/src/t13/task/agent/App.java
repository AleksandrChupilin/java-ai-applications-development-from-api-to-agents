package t13.task.agent;

import commons.Constants;
import commons.exceptions.TaskNotImplementedException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import t13.task.agent.clients.HttpMcpClient;
import t13.task.agent.clients.StdioMcpClient;
import t13.task.agent.models.SkillLoader;
import t13.task.agent.models.SkillMetadata;
import t13.task.agent.tools.BaseTool;
import t13.task.agent.tools.McpTool;
import t13.task.agent.tools.ReadSkillTool;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication(
        scanBasePackages = "t13.task.agent",
        exclude = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                ReactiveSecurityAutoConfiguration.class
        }
)
public class App {

    public static void main(String[] args) {
        //TODO:
        // - Read REDIS_HOST env var (default "localhost") and REDIS_PORT env var (default "6379")
        // - Create a SpringApplication for App.class and set default properties:
        //   server.port=8011, spring.data.redis.host, spring.data.redis.port,
        //   spring.ai.mcp.server.enabled=false, spring.ai.mcp.client.enabled=false
        // - Run the application
    }

    @Bean(destroyMethod = "close")
    public HttpMcpClient umsMcpClient() {
        //TODO:
        // - Read UMS_MCP_URL env var (default "http://localhost:8005/mcp")
        // - Create new HttpMcpClient(url), call connect(), and return it
        throw new TaskNotImplementedException();
    }

    @Bean(destroyMethod = "close")
    public StdioMcpClient duckduckgoMcpClient() {
        //TODO:
        // - Create new StdioMcpClient with dockerImage "khshanovskyi/ddg-mcp-server:latest"
        //   (pass null for command, args, and env)
        // - Call connect() and return it
        throw new TaskNotImplementedException();
    }

    @Bean
    public Guardrail guardrail() {
        //TODO:
        // - Return new Guardrail()
        throw new TaskNotImplementedException();
    }

    @Bean
    public UmsAgent umsAgent(HttpMcpClient umsMcpClient, StdioMcpClient duckduckgoMcpClient, Guardrail guardrail) {
        //TODO:
        // - Resolve skillsDir via skillsDir()
        // - Create a tools list; add new ReadSkillTool(skillsDir)
        // - For each McpToolModel from umsMcpClient.getMcpTools(), add new McpTool(umsMcpClient, t)
        // - For each McpToolModel from duckduckgoMcpClient.getMcpTools(), add new McpTool(duckduckgoMcpClient, t)
        // - Return new UmsAgent(Constants.OPENAI_API_KEY, Constants.GPT_5_4, tools, guardrail)
        throw new TaskNotImplementedException();
    }

    @Bean
    public ConversationManager conversationManager(UmsAgent umsAgent, StringRedisTemplate redis) {
        //TODO:
        // - Load skills via SkillLoader.loadSkills(skillsDir())
        // - Build the system prompt via buildSystemPrompt(skills)
        // - Return new ConversationManager(umsAgent, redis, systemPrompt)
        throw new TaskNotImplementedException();
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
                configurer.setDefaultTimeout(-1);
            }
        };
    }

    private static Path skillsDir() {
        //TODO:
        // - Read SKILLS_DIR env var; if non-null, return Path.of(env)
        // - Otherwise return Path.of("tasks/src/t13/task/_skills")
        throw new TaskNotImplementedException();
    }

    private static String buildSystemPrompt(List<SkillMetadata> skills) {
        //TODO:
        // - Build an XML <available_skills> block: for each SkillMetadata emit a <skill name="...">
        //   element with <description>, and optionally <license>, <compatibility>,
        //   <metadata> (child elements per entry), and <allowed-tools> (space-joined list)
        // - Wrap the XML block in a system prompt template that instructs the agent to call
        //   read_skill with the skill's SKILL.md path before acting on any skill-related request
        throw new TaskNotImplementedException();
    }
}
