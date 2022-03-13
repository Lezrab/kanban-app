package fr.istic.sir.kanban.aarzel.kanbanapp.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse extends GenericErrorResponse {

    private String message;

    private String error;

    public ErrorResponse(String timestamp, String methodInvoked, String urlInvoked, HttpStatus httpStatus, String message, String error) {
        super(timestamp, methodInvoked, urlInvoked, httpStatus);
        this.message = message;
        this.error = error;
    }
}
