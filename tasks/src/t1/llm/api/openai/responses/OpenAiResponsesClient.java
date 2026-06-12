package t1.llm.api.openai.responses;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.http.StreamResponse;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.ResponsesModel;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseOutputMessage;
import com.openai.models.responses.ResponseStreamEvent;
import commons.model.Message;
import commons.model.Role;
import t1.llm.api.openai.BaseOpenAiClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OpenAI Responses API client using the official OpenAI Java SDK.
 * <p>
 * The Responses API differs from Chat Completions: it uses {@code instructions} for the system
 * prompt and {@code input} for the conversation history. Compare with
 * {@link CustomOpenAiResponsesClient} which demonstrates the raw HTTP layer.
 */
public class OpenAiResponsesClient extends BaseOpenAiClient {

    private final OpenAIClient client;

    public OpenAiResponsesClient(String endpoint, String modelName, String apiKey, String systemPrompt) {
        super(endpoint, modelName, apiKey, systemPrompt);
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    @Override
    public Message response(List<Message> messages) {
        ResponseCreateParams params = buildParams(messages);
        Response response = client.responses().create(params);
        String content = response.output().stream()
                .filter(ResponseOutputItem::isMessage)
                .map(ResponseOutputItem::asMessage)
                .map(ResponseOutputMessage::content)
                .flatMap(List::stream)
                .filter(ResponseOutputMessage.Content::isOutputText)
                .map(part -> part.asOutputText().text())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No output_text found in Responses API output"));
        System.out.println(content);
        return new Message(Role.ASSISTANT, content);
    }

    @Override
    public Message streamResponse(List<Message> messages) {
        ResponseCreateParams params = buildParams(messages);
        StringBuilder accumulatedContent = new StringBuilder();
        try (StreamResponse<ResponseStreamEvent> stream = client.responses().createStreaming(params)) {
            stream.stream()
                    .filter(ResponseStreamEvent::isOutputTextDelta)
                    .map(event -> event.asOutputTextDelta().delta())
                    .forEach(delta -> {
                        System.out.print(delta);
                        accumulatedContent.append(delta);
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error during streaming API request: " + e.getMessage(), e);
        }
        System.out.println();
        return new Message(Role.ASSISTANT, accumulatedContent.toString());
    }

    private ResponseCreateParams buildParams(List<Message> messages) {
        List<ResponseInputItem> items = messages.stream()
                .map(message -> ResponseInputItem.ofEasyInputMessage(
                        EasyInputMessage.builder()
                                .role(EasyInputMessage.Role.of(message.role().getValue()))
                                .content(message.content())
                                .build()
                ))
                .toList();
        return ResponseCreateParams.builder()
                .model(ResponsesModel.ofString(modelName))
                .instructions(systemPrompt)
                .inputOfResponse(items)
                .build();
    }
}
