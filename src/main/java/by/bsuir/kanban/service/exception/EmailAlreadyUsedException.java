package by.bsuir.kanban.service.exception;

public class EmailAlreadyUsedException extends Exception {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
