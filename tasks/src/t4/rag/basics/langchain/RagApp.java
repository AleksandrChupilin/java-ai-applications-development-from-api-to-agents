package t4.rag.basics.langchain;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import commons.Constants;
import commons.exceptions.TaskNotImplementedException;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RagApp {

    private static final String SYSTEM_PROMPT = """
            You are a RAG-powered assistant that assists users with their questions about microwave usage.

            ## Structure of User message:
            `RAG CONTEXT` - Retrieved documents relevant to the query.
            `USER QUESTION` - The user's actual question.

            ## Instructions:
            - Use information from `RAG CONTEXT` as context when answering the `USER QUESTION`.
            - Cite specific sources when using information from the context.
            - Answer ONLY based on conversation history and RAG context.
            - If no relevant information exists in `RAG CONTEXT` or conversation history, state that you cannot answer the question.
            """;

    private static final String USER_PROMPT_TEMPLATE =
            "##RAG CONTEXT:\n{context}\n\n\n##USER QUESTION: \n{query}";

    private static final String MANUAL_PATH = "tasks/src/t4/rag/basics/microwave_manual.txt";
    private static final Path INDEX_PATH = Paths.get("tasks/src/t4/rag/basics/langchain/microwave_index.json");

    private final EmbeddingModel embeddingModel;
    private final OpenAIClient openAiClient;
    private final EmbeddingStore<TextSegment> embeddingStore;

    private RagApp(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
        this.openAiClient = OpenAIOkHttpClient.builder()
                .apiKey(Constants.OPENAI_API_KEY)
                .build();
        this.embeddingStore = setupEmbeddingStore();
    }

    private EmbeddingStore<TextSegment> setupEmbeddingStore() {
        //TODO:
        // - Print a startup message
        // - Check if INDEX_PATH already exists on disk
        // - If yes: load and return the store using InMemoryEmbeddingStore.fromFile(), print a confirmation message
        //   https://github.com/langchain4j/langchain4j/blob/main/docs/docs/integrations/embedding-stores/1-in-memory.md
        // - If no: call createNewIndex() and return its result
        throw new TaskNotImplementedException();
    }

    private InMemoryEmbeddingStore<TextSegment> createNewIndex() {
        //TODO:
        // - Print a loading message
        // - Load the document from MANUAL_PATH using FileSystemDocumentLoader.loadDocument()
        // - Print a splitting message
        // - Split the document into chunks using DocumentSplitters.recursive(300, 50)
        // - Print the number of chunks created
        // - Print an embedding/indexing message
        // - Create a new InMemoryEmbeddingStore<TextSegment>
        // - Embed all segments using embeddingModel.embedAll(segments).content()
        // - Add all embeddings and segments to the store using store.addAll()
        // - Persist the store to INDEX_PATH using store.serializeToFile()
        // - Print saved and success messages
        // - Return the store
        throw new TaskNotImplementedException();
    }

    private String retrieveContext(String query, int k, double minScore) {
        //TODO:
        // - Print the RETRIEVAL step header
        // - Print the query and search parameters (k, minScore)
        // - Embed the query using embeddingModel.embed(query).content()
        // - Build an EmbeddingSearchRequest with queryEmbedding, maxResults(k), minScore
        // - Search the embeddingStore using embeddingStore.search(request)
        // - Iterate over matches: collect each TextSegment content, print relevance score and content
        // - Return all collected parts joined with "\n\n"
        throw new TaskNotImplementedException();
    }

    private String augmentPrompt(String query, String context) {
        //TODO:
        // - Print the AUGMENTATION step header
        // - Replace {context} and {query} placeholders in USER_PROMPT_TEMPLATE
        // - Print the resulting augmented prompt
        // - Return the formatted string
        throw new TaskNotImplementedException();
    }

    private String generateAnswer(String augmentedPrompt) {
        //TODO:
        // - Print the GENERATION step header
        // - Build ChatCompletionCreateParams with model, temperature(0.0), system message (SYSTEM_PROMPT), user message (augmentedPrompt)
        // - Call openAiClient.chat().completions().create(params)
        // - Extract the answer from choices[0].message().content()
        // - Print the answer
        // - Return the answer string
        throw new TaskNotImplementedException();
    }

    public static void main(String[] args) {
        //TODO:
        // - Instantiate RagApp passing an OpenAiEmbeddingModel built with apiKey and modelName "text-embedding-3-small"
        // - Print a welcome message
        // - Create a Scanner and start an infinite loop reading user input
        // - Skip blank lines
        // - Step 1 (Retrieval):   call retrieveContext(query, 4, 0.7)
        // - Step 2 (Augmentation): call augmentPrompt(query, context)
        // - Step 3 (Generation):  call generateAnswer(augmented)
    }
}
