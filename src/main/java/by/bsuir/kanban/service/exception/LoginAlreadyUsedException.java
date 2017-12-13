package by.bsuir.kanban.service.exception;

public class LoginAlreadyUsedException extends Exception {
    public LoginAlreadyUsedException(String message) {
        super(message);
    }
}
