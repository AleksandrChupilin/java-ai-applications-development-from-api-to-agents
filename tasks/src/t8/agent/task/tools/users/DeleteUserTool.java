package t8.agent.task.tools.users;

import commons.exceptions.TaskNotImplementedException;
import commons.user.service.UserServiceClient;

import java.util.Map;

public class DeleteUserTool extends BaseUserServiceTool {

    public DeleteUserTool(UserServiceClient userClient) {
        super(userClient);
    }

    @Override
    public String getName() {
        //TODO: Return tool name "delete_users"
        throw new TaskNotImplementedException();
    }

    @Override
    public String getDescription() {
        //TODO: Return a short description of what this tool does
        throw new TaskNotImplementedException();
    }

    @Override
    public String getInputSchema() {
        //TODO: Return JSON schema — accepts user `id` (number) as a required parameter
        throw new TaskNotImplementedException();
    }

    @Override
    public String execute(Map<String, Object> arguments) {
        //TODO:
        // - Extract `id` as int from arguments (cast via Number)
        // - Call `userClient.deleteUser(id)` and return the result
        // - Wrap in try-catch and return error string on exception
        throw new TaskNotImplementedException();
    }
}
