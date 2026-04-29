package t13.task.agent.tools;

import commons.exceptions.TaskNotImplementedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ReadSkillTool extends BaseTool {

    private final Path skillsDir;

    public ReadSkillTool(Path skillsDir) {
        this.skillsDir = skillsDir.toAbsolutePath().normalize();
    }

    @Override
    protected String executeInternal(Map<String, Object> arguments) throws Exception {
        //TODO:
        // - Get "path" from arguments (cast to String), strip any leading '/' characters
        // - Resolve the sanitized path against skillsDir and normalize the result
        // - If the resolved path doesn't exist, return "ERROR: File not found: <fullPath>"
        // - If it is not a regular file, return "ERROR: Not a file: <fullPath>"
        // - Read and return the file's text content via Files.readString()
        throw new TaskNotImplementedException();
    }

    @Override
    public String getName() {
        //TODO:
        // - Return the tool name "read_skill"
        throw new TaskNotImplementedException();
    }

    @Override
    public String getDescription() {
        //TODO:
        // - Return a description explaining the tool reads skill files by relative path
        //   (hint: mention paths are relative to the skills root, e.g. /sample/SKILL.md)
        throw new TaskNotImplementedException();
    }

    @Override
    public Map<String, Object> getParameters() {
        //TODO:
        // - Return a JSON Schema Map: type=object, single required "path" string property
        //   (describe the path convention in the property description)
        throw new TaskNotImplementedException();
    }
}
