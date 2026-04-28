package t12.skills.task;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.beta.AnthropicBeta;
import com.anthropic.models.beta.messages.BetaCodeExecutionTool20250825;
import com.anthropic.models.beta.messages.BetaContainerParams;
import com.anthropic.models.beta.messages.BetaMessage;
import com.anthropic.models.beta.messages.BetaMessageParam;
import com.anthropic.models.beta.messages.BetaSkillParams;
import com.anthropic.models.beta.messages.MessageCreateParams;
import com.anthropic.models.beta.skills.SkillCreateParams;
import com.anthropic.models.beta.skills.SkillCreateResponse;
import com.anthropic.models.beta.skills.SkillDeleteParams;
import com.anthropic.models.beta.skills.SkillListParams;
import com.anthropic.models.beta.skills.SkillListResponse;
import com.anthropic.models.beta.skills.versions.VersionDeleteParams;
import com.anthropic.models.beta.skills.versions.VersionListParams;
import com.anthropic.models.beta.skills.versions.VersionListResponse;
import com.anthropic.models.messages.Model;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commons.Constants;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Anthropic beta skills agent — equivalent of anthropic_app.py.
 *
 * Uses the Anthropic SDK (AnthropicClient) to upload a skill directory, then
 * runs a multi-turn chat loop that reuses the container across turns so the
 * code execution environment persists session state.
 *
 * Beta headers required:
 *   anthropic-beta: code-execution-2025-08-25, skills-2025-10-02
 */
public class AnthropicApp {

    private static final String SKILLS_VERSION = "skills-2025-10-02";
    private static final String CODE_EXEC_VERSION = "code-execution-2025-08-25";

    private static final Path BASE_SKILLS_DIR = Path.of("tasks/src/t12/skills/_skills");
    private static final String STYLE_SKILL_TITLE = "style-guide";
    private static final String CALCULATOR_SKILL_TITLE = "calculator";

    private final AnthropicClient client;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public AnthropicApp() {
        this.client = AnthropicOkHttpClient.builder()
                .apiKey(Constants.ANTHROPIC_API_KEY)
                .build();
    }

    public static void main(String[] args) throws Exception {
        new AnthropicApp().run();
    }

    private void run() throws Exception {
        //TODO:
        // - Call getOrCreateSkill (choose STYLE_SKILL or CALCULATOR_SKILL dir/title to test)
        // - Call chat with the skill_id
        // - Call deleteSkills to clean up after the session
    }

    // ── Skill management ────────────────────────────────────────────────────

    private String getOrCreateSkill(String title, Path skillDir) throws Exception {
        //TODO:
        // - List all custom skills using the beta skills API (source="custom", betas=[SKILLS_VERSION])
        // - If a skill with matching displayTitle already exists, print its info and return its ID
        // - Otherwise call createSkill to upload a new skill and return its ID
        throw new commons.exceptions.TaskNotImplementedException();
    }

    private String createSkill(String title, Path skillDir) throws Exception {
        //TODO:
        // - Build SkillCreateParams with the title, beta header, and all files from skillDir
        // - Call client.beta().skills().create() and return the new skill ID
        throw new commons.exceptions.TaskNotImplementedException();
    }

    private void deleteSkills() throws Exception {
        //TODO:
        // - List all custom skills
        // - For each skill, list all its versions and delete each one using client.beta().skills().versions().delete()
        // - Then delete the skill itself using client.beta().skills().delete()
    }

    // ── Chat loop ────────────────────────────────────────────────────────────

    private void chat(String skillId) throws Exception {
        //TODO:
        // - Build a multi-turn chat loop (break on "exit")
        // - Create BetaContainerParams with the skill reference (type "custom", skill_id, version "latest")
        // - If containerId is set, include it in BetaContainerParams to reuse the running container
        // - Build MessageCreateParams (model, maxTokens, messages, container, tools, betas)
        // - Call client.beta().messages().create()
        // - Print the assistant reply and update containerId if present in the response
        // - Append assistant message to history for the next turn
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private String pretty(Object obj) throws Exception {
        if (obj instanceof String s) return s;
        return mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(mapper.readValue(mapper.writeValueAsString(obj), Object.class));
    }
}
