package commons.exceptions;

public class TaskNotImplementedException extends RuntimeException {

    public TaskNotImplementedException() {
        this("Task is not implemented yet");
    }

    public TaskNotImplementedException(String message) {
        super(message);
    }
}
