package by.bsuir.kanban.controller.exception;

import org.springframework.validation.Errors;

/**
 * Created by ulza1116 on 5/2/2017.
 */
public class InvalidObjectException extends RuntimeException {
    private Errors errors;

    public InvalidObjectException(Errors errors){
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
