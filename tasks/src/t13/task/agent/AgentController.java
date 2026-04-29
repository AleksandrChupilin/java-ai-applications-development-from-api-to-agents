package t13.task.agent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.exceptions.TaskNotImplementedException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AgentController {

    private final ConversationManager conversationManager;
    private final ObjectMapper mapper = new ObjectMapper();

    public AgentController(ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of("status", "healthy");
    }

    @PostMapping("/conversations")
    public Map<String, Object> createConversation(@RequestBody(required = false) CreateConversationRequest req) {
        //TODO:
        // - Extract title from req (null-safe)
        // - Delegate to conversationManager.createConversation(title) and return the result
        throw new TaskNotImplementedException();
    }

    @GetMapping("/conversations")
    public List<Map<String, Object>> listConversations() {
        //TODO:
        // - Delegate to conversationManager.listConversations() and return the result
        throw new TaskNotImplementedException();
    }

    @GetMapping("/conversations/{id}")
    public ResponseEntity<Map<String, Object>> getConversation(@PathVariable String id) {
        //TODO:
        // - Call conversationManager.getConversation(id)
        // - If null, return ResponseEntity.notFound().build()
        // - Otherwise return ResponseEntity.ok(conversation)
        throw new TaskNotImplementedException();
    }

    @DeleteMapping("/conversations/{id}")
    public ResponseEntity<Map<String, Object>> deleteConversation(@PathVariable String id) {
        //TODO:
        // - Call conversationManager.deleteConversation(id)
        // - If not deleted, return ResponseEntity.notFound().build()
        // - Otherwise return ResponseEntity.ok() with a success message Map
        throw new TaskNotImplementedException();
    }

    @PostMapping("/conversations/{id}/chat")
    public void chat(
            @PathVariable String id,
            @RequestBody ChatRequest request,
            HttpServletResponse response
    ) throws IOException {
        //TODO:
        // - Call conversationManager.getConversation(id); if null, write 404 JSON error to response and return
        // - If request.stream() is true:
        //   - Set content-type "text/event-stream;charset=UTF-8", Cache-Control and X-Accel-Buffering headers
        //   - Call conversationManager.streamChat(id, request.message(), response.getOutputStream())
        // - Otherwise:
        //   - Set content-type "application/json;charset=UTF-8"
        //   - Call conversationManager.chat(id, request.message()), serialize via mapper, write to response writer
    }

    public record CreateConversationRequest(String title) {}

    public record ChatRequest(
            Message message,
            @JsonProperty("stream") boolean stream
    ) {
        public ChatRequest { if (message == null) throw new IllegalArgumentException("message is required"); }
    }
}
