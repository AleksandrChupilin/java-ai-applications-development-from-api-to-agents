package t9.mcp.fundamentals.mcp.server;

import commons.exceptions.TaskNotImplementedException;
import commons.user.service.UserCreate;
import commons.user.service.UserSearchRequest;
import commons.user.service.UserServiceClient;
import commons.user.service.UserUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springaicommunity.mcp.annotation.McpResource;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

// https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html
// https://docs.spring.io/spring-ai/reference/api/mcp/mcp-annotations-server.html
@Component
public class UmsMcpServer {

    private static final Logger log = LoggerFactory.getLogger(UmsMcpServer.class);

    //TODO:
    // - define SEARCH_ASSISTANT_PROMPT: list available params (name, surname, email, gender),
    //   describe search strategy, effective combinations, and useful tips
    private static final String SEARCH_ASSISTANT_PROMPT = null;

    //TODO:
    // - define PROFILE_CREATION_PROMPT: list required vs optional fields,
    //   include address/credit card guidelines and a diversity note
    private static final String PROFILE_CREATION_PROMPT = null;

    // ==================== TOOL DEFINITIONS ====================

    @McpTool(name = "get_user_by_id", description = "Provides full user information by id")
    public String getUserById(@McpToolParam int userId) {
        //TODO:
        // - log invocation with userId
        // - call UserServiceClient().getUser(userId), log completion, and return the result
        // - catch Exception: log error and return "Error: " + e.getMessage()
        throw new TaskNotImplementedException();
    }

    @McpTool(name = "delete_user", description = "Deletes user")
    public String deleteUser(@McpToolParam int userId) {
        //TODO:
        // - log invocation with userId
        // - call UserServiceClient().deleteUser(userId), log completion, and return the result
        // - catch Exception: log error and return "Error: " + e.getMessage()
        throw new TaskNotImplementedException();
    }

    @McpTool(name = "search_user", description = "Searches for users by name, surname, email and gender")
    public String searchUser(@McpToolParam UserSearchRequest userSearchRequest) {
        //TODO:
        // - log invocation with userSearchRequest
        // - call UserServiceClient().searchUsers(name, surname, email, gender) using fields from userSearchRequest, log completion, and return the result
        // - catch Exception: log error and return "Error: " + e.getMessage()
        throw new TaskNotImplementedException();
    }

    @McpTool(name = "add_user", description = "Adds new user into the system")
    public String addUser(@McpToolParam UserCreate userCreate) {
        //TODO:
        // - log invocation with userCreate.email()
        // - call UserServiceClient().addUser(userCreate), log completion, and return the result
        // - catch Exception: log error and return "Error: " + e.getMessage()
        throw new TaskNotImplementedException();
    }

    @McpTool(name = "update_user", description = "Updates user by userId")
    public String updateUser(@McpToolParam int userId, @McpToolParam UserUpdate userUpdate) {
        //TODO:
        // - log invocation with userId
        // - call UserServiceClient().updateUser(userId, userUpdate), log completion, and return the result
        // - catch Exception: log error and return "Error: " + e.getMessage()
        throw new TaskNotImplementedException();
    }

    // ==================== MCP RESOURCES ====================

    @McpResource(
            uri = "users-management://flow-diagram",
            name = "flow-diagram",
            mimeType = "image/png",
            description = "The Users Management Service flow diagram as PNG image"
    )
    public String flowDiagramResource() {
        //TODO:
        // - read flow.png via Files.readAllBytes(Path.of("tasks/src/t9/mcp/fundamentals/flow.png"))
        // - return Base64.getEncoder().encodeToString(bytes)
        // - wrap IOException in RuntimeException
        throw new TaskNotImplementedException();
    }

    // ==================== MCP PROMPTS ====================

    @McpPrompt(description = "users formulate effective search queries")
    public String searchAssistantPrompt() {
        //TODO:
        // - return SEARCH_ASSISTANT_PROMPT
        throw new TaskNotImplementedException();
    }

    @McpPrompt(description = "Guides creation of realistic user profiles")
    public String profileCreationPrompt() {
        //TODO:
        // - return PROFILE_CREATION_PROMPT
        throw new TaskNotImplementedException();
    }

}
