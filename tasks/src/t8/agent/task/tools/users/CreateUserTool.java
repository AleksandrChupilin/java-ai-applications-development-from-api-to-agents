package t8.agent.task.tools.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.exceptions.TaskNotImplementedException;
import commons.user.service.UserCreate;
import commons.user.service.UserServiceClient;

import java.util.Map;

public class CreateUserTool extends BaseUserServiceTool {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CreateUserTool(UserServiceClient userClient) {
        super(userClient);
    }

    @Override
    public String getName() {
        //TODO: Return tool name "add_user"
        throw new TaskNotImplementedException();
    }

    @Override
    public String getDescription() {
        //TODO: Return a short description of what this tool does
        throw new TaskNotImplementedException();
    }

    @Override
    public String getInputSchema() {
        //TODO: Return JSON schema — required: name, surname, email, about_me;
        //   optional: phone, date_of_birth, address (country/city/street/flat_house),
        //   gender, company, salary, credit_card (num/cvv/exp_date)
        throw new TaskNotImplementedException();
    }

    @Override
    public String execute(Map<String, Object> arguments) {
        //TODO:
        // - Convert `arguments` to `UserCreate` via `objectMapper.convertValue(arguments, UserCreate.class)`
        // - Call `userClient.addUser(user)` and return the result
        // - Wrap in try-catch and return error string on exception
        throw new TaskNotImplementedException();
    }
}
