# Work with AI APIs (Java)

In this task, you will work with APIs from different AI vendors. The goal is to understand how to make calls to
different models, how to parse responses, how to work with streaming, and how these features work under the hood in the
libraries we commonly use.

---

## Prerequisites

**Java 25** and **Maven 3.9+**

**API Keys** to work with different models (you will need to pay ~5-10$ credits):

- **OpenAI API Key** — set as environment variable `OPENAI_API_KEY`
- **Anthropic API Key** — set as environment variable `ANTHROPIC_API_KEY`
- **Gemini API Key** — set as environment variable `GEMINI_API_KEY`

---

## Task

1. **[Import](https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-data) in Postman [collection](dial-ai-course.postman_collection.json). It will be quite useful for the further tasks. In the collection present OPENAI_API_KEY, ANTHROPIC_API_KEY and GEMINI_API_KEY environment variables, [here you can find how to configure environment in Postman](https://learning.postman.com/docs/sending-requests/variables/managing-environments)**
2. Implement [AiClient.java](AiClient.java) — abstract base class with `response()` and `streamResponse()`
3. Implement [BaseApp.java](BaseApp.java) — interactive chat loop using Scanner

### OpenAI Chat Completions

1. Implement [BaseOpenAiClient.java](openai/BaseOpenAiClient.java) — validates and prepends "Bearer " to API key
2. Implement [OpenAiChatCompletionsClient.java](openai/chat/completions/OpenAiChatCompletionsClient.java) — SDK client
3. Run [OpenAiChatCompletionsApp.java](openai/chat/completions/OpenAiChatCompletionsApp.java) with `sdkClient` and test
4. Implement [CustomOpenAiChatCompletionsClient.java](openai/chat/completions/CustomOpenAiChatCompletionsClient.java) — raw
   HTTP client
5. Optional: add loging for request and response JSONs to see it while request/response flow
6. In [OpenAiChatCompletionsApp.java](openai/chat/completions/OpenAiChatCompletionsApp.java) switch to `customClient` and test

### OpenAI Responses API

1. Implement [OpenAiResponsesClient.java](openai/responses/OpenAiResponsesClient.java) — SDK client
2. Run [OpenAiResponsesApp.java](openai/responses/OpenAiResponsesApp.java) with `sdkClient` and test 
3. Implement [CustomOpenAiResponsesClient.java](openai/responses/CustomOpenAiResponsesClient.java) — raw HTTP client 
4. Optional: add loging for request and response JSONs to see it while request/response flow 
5. In [OpenAiResponsesApp.java](openai/responses/OpenAiResponsesApp.java) switch to `customClient` and test

### Anthropic
 
1. Implement [AnthropicAiClient.java](anthropic/AnthropicAiClient.java) — SDK client 
2. Run [AnthropicApp.java](anthropic/AnthropicApp.java) with `sdkClient` and test 
3. Implement [CustomAnthropicAiClient.java](anthropic/CustomAnthropicAiClient.java) — raw HTTP client 
4. In [AnthropicApp.java](anthropic/AnthropicApp.java) switch to `customClient` and test

### Gemini

1. Implement [GeminiAiClient.java](gemini/GeminiAiClient.java) — SDK client
2. Run [GeminiApp.java](gemini/GeminiApp.java) with `sdkClient` and test
3. Implement [CustomGeminiAiClient.java](gemini/CustomGeminiAiClient.java) — raw HTTP client (no stable Java SDK available)
4. Optional: add loging for request and response JSONs to see it while request/response flow
5. [GeminiApp.java](gemini/GeminiApp.java) switch to `customClient` and test

---

**Congratulations 🎉 Now you know that AI APIs are not magic — they are plain REST (with SSE for streaming)!**
