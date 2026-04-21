package commons.model;

import java.util.Map;

public record Message(Role role, String content) {

    public Map<String, Object> toMap() {
        return Map.of("role", role.getValue(), "content", content);
    }
}
