package t13.task.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonValue;
import com.openai.helpers.ChatCompletionAccumulator;
import com.openai.models.FunctionDefinition;
import com.openai.models.FunctionParameters;
import com.openai.models.chat.completions.*;
import commons.exceptions.TaskNotImplementedException;
import t13.task.agent.tools.BaseTool;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class UmsAgent {

    private OpenAIClient openAIClient;
    private final ObjectMapper mapper = new ObjectMapper();
    private String model;
    private Map<String, BaseTool> tools;
    private List<ChatCompletionTool> toolSchemas;
    private Guardrail guardrail;

    public UmsAgent(String apiKey, String model, List<BaseTool> tools, Guardrail guardrail) {
        //TODO:
        // - Assign model and guardrail fields
        // - Build this.tools as Map<String, BaseTool> via Collectors.toMap(BaseTool::getName, ...)
        // - Build this.toolSchemas by streaming tools, mapping each getSchema() through convertTool()
        // - Initialize openAIClient via OpenAIOkHttpClient.builder().apiKey(apiKey).build()
    }

    public Message response(List<Message> messages) {
        //TODO:
        // - Build ChatCompletionCreateParams with model, toolSchemas, and toSdkMessages(messages)
        // - Call openAIClient.chat().completions().create() and extract the first choice message
        // - Extract toolCalls from the response; wrap the result into a Message via fromSdkMessage()
        // - If toolCalls non-empty: add aiMessage to messages, call callTools(), recurse with response()
        // - Otherwise return aiMessage
        throw new TaskNotImplementedException();
    }

    public void streamResponse(List<Message> messages, OutputStream out) throws IOException {
        //TODO:
        // - Build ChatCompletionCreateParams with model, toolSchemas, and toSdkMessages(messages)
        // - Create a ChatCompletionAccumulator
        // - Open a streaming session via openAIClient.chat().completions().createStreaming()
        // - For each chunk: call accumulator.accumulate(); if delta.content is non-empty,
        //   serialize as SSE JSON {"choices":[{"delta":{"content":"..."},"index":0,"finish_reason":null}]}
        //   and write via writeSse()
        // - After the stream closes, extract the accumulated message and its toolCalls
        // - If toolCalls non-empty:
        //   a. Convert to Message via fromSdkMessage() and add to messages
        //   b. Write a "tool_activity" call-event SSE for each toolCall (type, name, arguments)
        //   c. Record messages.size() as prevLen; call callTools(); slice the new tool-result messages
        //   d. Write a "tool_activity" result-event SSE for each result (type, name, content)
        //   e. Recurse via streamResponse() and return
        // - If no toolCalls: add final assistant Message; write empty-delta SSE then "[DONE]" via writeSse()
    }

    @SuppressWarnings("unchecked")
    private void callTools(List<ChatCompletionMessageToolCall> toolCalls, List<Message> messages) {
        //TODO:
        // - For each toolCall: extract toolCallId, toolName, and argumentsJson
        // - Look up the BaseTool by toolName in this.tools
        // - Parse argumentsJson into Map<String, Object> via mapper.readValue()
        // - Execute tool.execute(toolCallId, arguments); apply guardrail.redact() on result content
        // - Add the result Message to messages
        // - If tool not found, add an error Message (role="tool", toolCallId set, content = error text)
    }

    private List<ChatCompletionMessageParam> toSdkMessages(List<Message> messages) {
        //TODO:
        // - Map each Message to a ChatCompletionMessageParam via toSdkMessage()
        throw new TaskNotImplementedException();
    }

    @SuppressWarnings("unchecked")
    private ChatCompletionMessageParam toSdkMessage(Message msg) {
        //TODO:
        // - Switch on msg.getRole():
        //   - "system"    → ChatCompletionMessageParam.ofSystem() with ChatCompletionSystemMessageParam
        //   - "user"      → ChatCompletionMessageParam.ofUser() with ChatCompletionUserMessageParam
        //   - "assistant" → ChatCompletionAssistantMessageParam.builder(); if toolCalls present, map each
        //                   to ChatCompletionMessageToolCall with id, type=JsonValue.from("function"),
        //                   and function(name + arguments); then yield ofAssistant()
        //   - "tool"      → ChatCompletionMessageParam.ofTool() with toolCallId and content
        //   - default     → throw IllegalArgumentException
        throw new TaskNotImplementedException();
    }

    private Message fromSdkMessage(ChatCompletionMessage msg, List<ChatCompletionMessageToolCall> toolCalls) {
        //TODO:
        // - Extract content via msg.content().orElse(null)
        // - If toolCalls non-empty, map each to a Map with "id", "type"="function",
        //   and "function" containing "name" and "arguments"
        // - Return new Message("assistant", content, null, toolCallMaps or null if empty)
        throw new TaskNotImplementedException();
    }

    @SuppressWarnings("unchecked")
    private ChatCompletionTool convertTool(Map<String, Object> toolMap) {
        //TODO:
        // - Extract "function" map, then name, description, and parameters from it
        // - Build FunctionParameters via FunctionParameters.builder(), adding each parameter entry
        //   via putAdditionalProperty(k, JsonValue.from(v))
        // - Return a ChatCompletionTool with type=JsonValue.from("function"), name, description, parameters
        throw new TaskNotImplementedException();
    }

    private void writeSse(OutputStream out, String data) throws IOException {
        //TODO:
        // - Write "data: " + data + "\n\n" as UTF-8 bytes to out, then flush
    }
}
