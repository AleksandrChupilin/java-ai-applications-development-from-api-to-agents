package t8.agent.task;

import commons.Constants;
import commons.model.Conversation;
import commons.model.Message;
import commons.model.Role;
import commons.user.service.UserServiceClient;
import t8.agent.task.agents.AnthropicBasedAgent;
import t8.agent.task.agents.BaseAgent;
import t8.agent.task.agents.OpenAIBasedAgent;
import t8.agent.task.tools.BaseTool;
import t8.agent.task.tools.WebSearchTool;
import t8.agent.task.tools.users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        //TODO:
        // - Create `UserServiceClient`
        // - Create list of all tools:
        //      - WebSearchTool (needs OPENAI_API_KEY),
        //      - GetUserByIdTool,
        //      - SearchUsersTool,
        //      - CreateUserTool,
        //      - UpdateUserTool,
        //      - DeleteUserTool
        // - Create OpenAIBasedAgent (or AnthropicBasedAgent) with all tools and Prompts.SYSTEM_PROMPT
        // - Create Conversation
        // - Print welcome message and run a Scanner loop until user types "exit":
        //   - Read user input, add Message(Role.USER, input) to conversation
        //   - Take a mutable copy of conversation messages; record its size before the call
        //   - Call agent.getResponse(messages, true) to get the AI reply
        //   - Sync any intermediate tool-call/tool-result messages back into conversation
        //   - Add the AI reply to conversation and print it
    }
}
