package t13.task.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.exceptions.TaskNotImplementedException;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class ConversationManager {

    private static final String CONVERSATION_PREFIX = "conversation:";
    private static final String CONVERSATION_LIST_KEY = "conversations:list";

    private final UmsAgent umsAgent;
    private final StringRedisTemplate redis;
    private final String systemPrompt;
    private final ObjectMapper mapper = new ObjectMapper();

    public ConversationManager(UmsAgent umsAgent, StringRedisTemplate redis, String systemPrompt) {
        this.umsAgent = umsAgent;
        this.redis = redis;
        this.systemPrompt = systemPrompt;
    }

    public Map<String, Object> createConversation(String title) {
        //TODO:
        // - Generate a random UUID id and capture Instant.now() as a timestamp string
        // - Build a LinkedHashMap: id, title (default to "New Conversation" if blank/null),
        //   messages=new ArrayList<>(), created_at, updated_at
        // - Persist to Redis: opsForValue().set(CONVERSATION_PREFIX + id, toJson(conversation))
        // - Add id to sorted set: opsForZSet().add(CONVERSATION_LIST_KEY, id, epochMillis)
        // - Return the conversation Map
        throw new TaskNotImplementedException();
    }

    public List<Map<String, Object>> listConversations() {
        //TODO:
        // - Fetch all ids from CONVERSATION_LIST_KEY via redis.opsForZSet().reverseRange(0, -1)
        // - If null, return empty list
        // - For each id, load JSON from Redis and parse it; skip if null
        // - Build a summary LinkedHashMap per conversation: id, title, created_at, updated_at,
        //   message_count (size of the "messages" list)
        // - Return the list of summaries
        throw new TaskNotImplementedException();
    }

    public Map<String, Object> getConversation(String id) {
        //TODO:
        // - Load JSON from Redis for CONVERSATION_PREFIX + id
        // - If null, return null; otherwise parse and return via fromJson()
        throw new TaskNotImplementedException();
    }

    public boolean deleteConversation(String id) {
        //TODO:
        // - Delete key CONVERSATION_PREFIX + id from Redis
        // - If deleted (non-null true), remove id from CONVERSATION_LIST_KEY sorted set and return true
        // - Otherwise return false
        throw new TaskNotImplementedException();
    }

    public Map<String, Object> chat(String conversationId, Message userMessage) {
        //TODO:
        // - Load messages via loadMessages(conversationId)
        // - If messages is empty, add a system Message with systemPrompt (null toolCallId, null toolCalls)
        // - Add userMessage to messages
        // - Call umsAgent.response(messages) to get aiMessage
        // - Persist updated messages via saveMessages(conversationId, messages)
        // - Return a Map with "content" (aiMessage content or "") and "conversation_id"
        throw new TaskNotImplementedException();
    }

    public void streamChat(String conversationId, Message userMessage, OutputStream out) throws IOException {
        //TODO:
        // - Write a SSE event containing {"conversation_id": conversationId} via writeSse()
        // - Load messages via loadMessages(); if empty, add system prompt Message; add userMessage
        // - Call umsAgent.streamResponse(messages, out)
        // - Persist updated messages via saveMessages(conversationId, messages)
    }

    private List<Message> loadMessages(String conversationId) {
        //TODO:
        // - Call getConversation(conversationId); if null, throw NoSuchElementException
        // - Extract "messages" as List<Map<String, Object>> from the conversation map
        // - If null, return an empty ArrayList
        // - Map each entry to a Message via mapper.convertValue() and collect into a mutable ArrayList
        throw new TaskNotImplementedException();
    }

    private void saveMessages(String conversationId, List<Message> messages) {
        //TODO:
        // - Load existing JSON from Redis for CONVERSATION_PREFIX + conversationId; if null, return
        // - Parse the JSON via fromJson(), replace "messages" with Message::toDict list, update "updated_at"
        // - Save back to Redis via opsForValue().set() and refresh the sorted set score
    }

    private void writeSse(OutputStream out, String data) throws IOException {
        //TODO:
        // - Write "data: " + data + "\n\n" as UTF-8 bytes to out, then flush
    }

    private String toJson(Object obj) {
        //TODO:
        // - Serialize obj to JSON string via mapper.writeValueAsString(); wrap any exception in RuntimeException
        throw new TaskNotImplementedException();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> fromJson(String json) {
        //TODO:
        // - Parse JSON string to LinkedHashMap via mapper.readValue(); wrap any exception in RuntimeException
        throw new TaskNotImplementedException();
    }
}
