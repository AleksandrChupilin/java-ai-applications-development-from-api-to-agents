package t8.agent.task.tools.users;

import commons.exceptions.TaskNotImplementedException;
import commons.user.service.UserServiceClient;

import java.util.Map;

public class SearchUsersTool extends BaseUserServiceTool {

    public SearchUsersTool(UserServiceClient userClient) {
        super(userClient);
    }

    @Override
    public String getName() {
        //TODO: Return tool name "search_users"
        throw new TaskNotImplementedException();
    }

    @Override
    public String getDescription() {
        //TODO: Return a short description of what this tool does
        throw new TaskNotImplementedException();
    }

    @Override
    public String getInputSchema() {
        //TODO: Return JSON schema — optional string properties: name, surname, email, gender (none required)
        throw new TaskNotImplementedException();
    }

    @Override
    public String execute(Map<String, Object> arguments) {
        //TODO:
        // - Extract optional name, surname, email, gender strings from arguments (may be null)
        // - Call `userClient.searchUsers(name, surname, email, gender)` and return the result
        // - Wrap in try-catch and return error string on exception
        throw new TaskNotImplementedException();
    }
}
