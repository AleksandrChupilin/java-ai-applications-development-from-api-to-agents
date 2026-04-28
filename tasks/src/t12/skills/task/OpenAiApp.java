package t12.skills.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonValue;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseInputItem;
import commons.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * OpenAI native skills agent — equivalent of openai_app.py.
 *
 * Skills management (list/create/delete) uses raw HTTP because the OpenAI Java
 * SDK 2.1.0 does not yet expose the Skills API. The chat loop uses the SDK's
 * Responses API (client.responses().create()), with the shell tool injected via
 * putAdditionalBodyProperty because the shell+environment type is also absent
 * from the SDK's typed Tool union.  previous_response_id chains turns
 * server-side so the full history does not need to be resent on every turn.
 */
public class OpenAiApp {

    private static final String OPENAI_API = "https://api.openai.com/v1";
    private static final Path BASE_SKILLS_DIR = Path.of("tasks/src/t12/skills/_skills");

    private static final String STYLE_SKILL_NAME = "style-guide";
    private static final Path STYLE_SKILL_DIR = BASE_SKILLS_DIR.resolve(STYLE_SKILL_NAME);

    private static final String CALCULATOR_SKILL_NAME = "calculator";
    private static final Path CALCULATOR_SKILL_DIR = BASE_SKILLS_DIR.resolve(CALCULATOR_SKILL_NAME);

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String apiKey = Constants.OPENAI_API_KEY;

    private final OpenAIClient openAIClient;

    public OpenAiApp() {
        this.openAIClient = OpenAIOkHttpClient.builder()
                .apiKey(Constants.OPENAI_API_KEY)
                .build();
    }

    public static void main(String[] args) throws Exception {
        new OpenAiApp().run();
    }

    private void run() throws Exception {
        //TODO:
        // - Call getOrCreateSkill (choose CALCULATOR or STYLE skill dir/name to test)
        // - Call chat with the skill_id
        // - Call deleteSkills to clean up after the session
    }

    // ── Skill management (raw HTTP — Skills API not yet in Java SDK) ─────────

    private String getOrCreateSkill(String skillName, Path skillDir) throws Exception {
        //TODO:
        // - List existing skills using raw HTTP (GET /v1/skills)
        // - Return the ID if one with matching name already exists
        // - Otherwise upload a new skill using createSkill() and return its ID
        throw new commons.exceptions.TaskNotImplementedException();
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> listSkills() throws Exception {
        //TODO:
        // - Build an HttpRequest for GET /v1/skills with Authorization header
        // - Call http.send() and parse the "data" array from the response body
        throw new commons.exceptions.TaskNotImplementedException();
    }

    private String createSkill(String skillName, Path skillDir) throws Exception {
        //TODO:
        // - Zip the skill directory using zipSkill()
        // - Build a multipart/form-data body (POST /v1/skills) with the zip file
        // - Call http.send() and return the new skill ID from the response
        throw new commons.exceptions.TaskNotImplementedException();
    }

    private void deleteSkills() throws Exception {
        //TODO:
        // - List all uploaded skills
        // - For each skill, call DELETE /v1/skills/{id} and print confirmation
    }

    // ── Chat loop (OpenAI SDK Responses API) ─────────────────────────────────

    private void chat(String skillId) throws Exception {
        //TODO:
        // - Build a multi-turn chat loop (break on "exit")
        // - Create a shell tool dict with "container_auto" environment referencing the skillId
        // - Build ResponseCreateParams (model, input with user message, shell tool via additional body property)
        // - If previousResponseId is set, include it in paramsBuilder.previousResponseId() to chain history
        // - Call openAIClient.responses().create() and print response.output_text()
        // - Save the new response.id() as previousResponseId for the next turn
    }

    // ── HTTP helpers (for Skills API) ─────────────────────────────────────────

    private HttpRequest openaiRequest(String method, String path, String jsonBody) {
        var builder = HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_API + path))
                .header("Authorization", "Bearer " + apiKey);

        if (jsonBody != null) {
            builder.header("Content-Type", "application/json")
                    .method(method, HttpRequest.BodyPublishers.ofString(jsonBody));
        } else {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        }
        return builder.build();
    }

    private void requireOk(HttpResponse<String> response) {
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("HTTP " + response.statusCode() + ": " + response.body());
        }
    }

    /**
     * Zips all files under skillDir, preserving paths relative to skillDir's parent.
     * Mirrors the zip_skill() function in openai_app.py.
     */
    private byte[] zipSkill(Path skillDir) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(buf)) {
            try (Stream<Path> paths = Files.walk(skillDir)) {
                for (Path file : paths.filter(Files::isRegularFile).toList()) {
                    String entryName = skillDir.getParent().relativize(file).toString().replace('\\', '/');
                    zip.putNextEntry(new ZipEntry(entryName));
                    zip.write(Files.readAllBytes(file));
                    zip.closeEntry();
                }
            }
        }
        return buf.toByteArray();
    }

    /**
     * Builds a multipart/form-data body with a single file part for the zip.
     */
    private byte[] buildMultipartBody(String boundary, String zipFileName, byte[] zipBytes) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String CRLF = "\r\n";
        String dash = "--";

        out.write((dash + boundary + CRLF).getBytes());
        out.write(("Content-Disposition: form-data; name=\"files\"; filename=\"" + zipFileName + ".zip\"" + CRLF).getBytes());
        out.write(("Content-Type: application/zip" + CRLF + CRLF).getBytes());
        out.write(zipBytes);
        out.write(CRLF.getBytes());

        out.write((dash + boundary + dash + CRLF).getBytes());
        return out.toByteArray();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String pretty(Object obj) throws Exception {
        if (obj instanceof String s) return s;
        return mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(mapper.readValue(mapper.writeValueAsString(obj), Object.class));
    }
}
