package by.bsuir.kanban.service.exception;


public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(Throwable cause) {
        super(cause);
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
