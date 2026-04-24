package t8.agent.task.tools.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.exceptions.TaskNotImplementedException;
import commons.user.service.UserServiceClient;
import commons.user.service.UserUpdate;

import java.util.Map;

public class UpdateUserTool extends BaseUserServiceTool {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UpdateUserTool(UserServiceClient userClient) {
        super(userClient);
    }

    @Override
    public String getName() {
        //TODO: Return tool name "update_user"
        throw new TaskNotImplementedException();
    }

    @Override
    public String getDescription() {
        //TODO: Return a short description of what this tool does
        throw new TaskNotImplementedException();
    }

    @Override
    public String getInputSchema() {
        //TODO: Return JSON schema — required: id (number, user ID to update);
        //   optional new_info object with updatable user fields (name, surname, email, phone,
        //   date_of_birth, address, gender, company, salary, credit_card)
        throw new TaskNotImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String execute(Map<String, Object> arguments) {
        //TODO:
        // - Extract `id` as int from arguments (cast via Number)
        // - Extract `new_info` as Map from arguments
        // - Convert `new_info` to `UserUpdate` via `objectMapper.convertValue(newInfo, UserUpdate.class)`
        // - Call `userClient.updateUser(userId, user)` and return the result
        // - Wrap in try-catch and return error string on exception
        throw new TaskNotImplementedException();
    }
}
