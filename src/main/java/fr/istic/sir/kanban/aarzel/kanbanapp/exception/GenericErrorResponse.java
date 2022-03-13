package fr.istic.sir.kanban.aarzel.kanbanapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class GenericErrorResponse {

    private String timestamp;

    private String methodInvoked;

    private String urlInvoked;

    private HttpStatus httpStatus;
}
