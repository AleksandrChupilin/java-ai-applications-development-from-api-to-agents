package t5.rag.advanced;

import commons.Constants;
import commons.model.Conversation;
import commons.model.Message;
import commons.model.Role;
import t5.rag.advanced.chat.ChatCompletionClient;
import t5.rag.advanced.embeddings.EmbeddingsClient;
import t5.rag.advanced.embeddings.SearchMode;
import t5.rag.advanced.embeddings.TextProcessor;

import java.util.List;
import java.util.Scanner;

public class App {

    private static final String MANUAL_PATH = "tasks/src/t5/rag/advanced/microwave_manual.txt";

    //TODO:
    // - describe the assistant as a RAG-powered assistant for microwave usage questions
    // - explain user message structure: RAG CONTEXT section (retrieved documents) and USER QUESTION section
    // - instruct LLM to: answer using RAG CONTEXT, cite sources, restrict answers to context/history,
    //   refuse questions unrelated to microwave usage or outside context scope
    private static final String SYSTEM_PROMPT = """
            """;

    //TODO:
    // - define structured template with ##RAG CONTEXT section and ##USER QUESTION section
    // - use {context} and {query} placeholders for runtime substitution
    private static final String USER_PROMPT_TEMPLATE = "";

    public static void main(String[] args) {
        //TODO:
        // - instantiate EmbeddingsClient: "text-embedding-3-small", Constants.OPENAI_EMBEDDINGS_ENDPOINT, Constants.OPENAI_API_KEY
        // - instantiate ChatCompletionClient: Constants.GPT_5_4, Constants.OPENAI_CHAT_COMPLETIONS_ENDPOINT, Constants.OPENAI_API_KEY
        // - instantiate TextProcessor: localhost, port 5433, database "vectordb", user "postgres", password "postgres"
        // - prompt user (y/n) to load context; on yes call textProcessor.processTextFile(MANUAL_PATH, 400, 40, 384)
        // - create Conversation, add SYSTEM message with SYSTEM_PROMPT
        // - run while loop:
        //   - read user input from console; exit on "quit"/"exit" or end of stream
        //   - STEP 1 RETRIEVAL: textProcessor.search(SearchMode.EUCLIDEAN_DISTANCE, userRequest, 5, 0.01, 384)
        //   - STEP 2 AUGMENTATION: fill USER_PROMPT_TEMPLATE {context} and {query}, add as USER message to conversation
        //   - STEP 3 GENERATION: completionClient.getCompletion(conversation.getMessages()), print and add response to conversation
        // NOTE: start docker-compose.yml before running to bring up Postgres + pgvector on port 5433
    }
}
