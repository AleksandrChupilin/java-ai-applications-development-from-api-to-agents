package t12.skills.task.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonValue;
import com.openai.helpers.ChatCompletionAccumulator;
import com.openai.models.FunctionDefinition;
import com.openai.models.FunctionParameters;
import com.openai.models.chat.completions.ChatCompletionAssistantMessageParam;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionMessage;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.chat.completions.ChatCompletionMessageToolCall;
import com.openai.models.chat.completions.ChatCompletionTool;
import com.openai.models.chat.completions.ChatCompletionToolMessageParam;
import t12.skills.task.custom.tools.BaseTool;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Agent {

    private final String model;
    private final List<BaseTool> tools;
    private final List<ChatCompletionTool> apiTools;
    private final OpenAIClient openAIClient;
    private final ObjectMapper objectMapper;

    public Agent(String apiKey, String model, List<BaseTool> tools) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("API key cannot be null or empty");
        }
        this.model = model;
        this.tools = tools;
        this.objectMapper = new ObjectMapper();
        this.openAIClient = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
        this.apiTools = tools.stream().map(t -> convertTool(t.getSchema())).toList();
    }

    public ChatCompletionMessageParam getCompletion(List<ChatCompletionMessageParam> messages) {
        //TODO:
        // - Call streamCompletion to get the model's response
        // - Build an assistant ChatCompletionMessageParam from the response
        // - If the response contains tool calls:
        //   - Append the assistant message to history
        //   - Call processToolCalls to execute tools and append results
        //   - Recursively call getCompletion and return the final result
        // - Otherwise return the assistant message
        throw new commons.exceptions.TaskNotImplementedException();
    }

    private ChatCompletionMessageParam buildAssistantParam(ChatCompletionMessage msg,
                                                            List<ChatCompletionMessageToolCall> toolCalls) {
        //TODO:
        // - Build ChatCompletionAssistantMessageParam with content and tool calls from msg
        // - Return it wrapped in ChatCompletionMessageParam.ofAssistant()
        throw new commons.exceptions.TaskNotImplementedException();
    }

    private ChatCompletionMessage streamCompletion(List<ChatCompletionMessageParam> messages) {
        //TODO:
        // - Build ChatCompletionCreateParams with model, tools, and messages
        // - Call openAIClient.chat().completions().createStreaming()
        // - Use ChatCompletionAccumulator to collect chunks and print content deltas to stdout
        // - Return the final accumulated ChatCompletionMessage
        throw new commons.exceptions.TaskNotImplementedException();
    }

    @SuppressWarnings("unchecked")
    private void processToolCalls(List<ChatCompletionMessageToolCall> toolCalls, List<ChatCompletionMessageParam> messages) {
        //TODO:
        // - For each tool call:
        //   - Parse arguments JSON and find the matching tool in self.tools
        //   - Call tool.execute() and append a ChatCompletionToolMessageParam with the result to messages
    }

    @SuppressWarnings("unchecked")
    private ChatCompletionTool convertTool(Map<String, Object> toolMap) {
        //TODO:
        // - Convert a raw tool map into an SDK ChatCompletionTool
        // - Map function name, description, and parameters using FunctionDefinition.builder()
        throw new commons.exceptions.TaskNotImplementedException();
    }
}
