package fr.istic.sir.kanban.aarzel.kanbanapp.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ErrorResponseForParam extends GenericErrorResponse {

    private List<Map<String, String>> paramErrors;

    public ErrorResponseForParam(String timestamp, String methodInvoked, String urlInvoked, HttpStatus httpStatus, List<Map<String, String>> paramErrors) {
        super(timestamp, methodInvoked, urlInvoked, httpStatus);
        this.paramErrors = paramErrors;
    }
}
