package t12.skills.task.custom.tools;

import t12.skills.task.custom.FileUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ReadSkill extends BaseTool {

    private final Path skillsDir;

    public ReadSkill(Path skillsDir) {
        this.skillsDir = skillsDir.toAbsolutePath().normalize();
    }

    @Override
    public String getName() {
        //TODO: Return the tool name
        throw new commons.exceptions.TaskNotImplementedException();
    }

    @Override
    public String getDescription() {
        //TODO: Return a description telling the agent when and how to use this tool (what it reads, what path format to use)
        throw new commons.exceptions.TaskNotImplementedException();
    }

    @Override
    public Map<String, Object> getParameters() {
        //TODO: Return the JSON schema for the tool parameters (required string "path")
        throw new commons.exceptions.TaskNotImplementedException();
    }

    @Override
    protected String doExecute(Map<String, Object> arguments) {
        //TODO:
        // - Get the path from arguments and strip the leading "/"
        // - Resolve the full filesystem path by combining self.skillsDir with the relative path
        // - Return the file content using FileUtils.getFileContent()
        throw new commons.exceptions.TaskNotImplementedException();
    }
}
