package fr.istic.sir.kanban.aarzel.kanbanapp.handler;

import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ErrorResponse;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ErrorResponseArgs;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceAlreadyExistsException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * General exception Handler that will :
 * - catch custom exceptions and,
 * - return an HTTP response containing an Error with an error code.
 */
@ControllerAdvice
public class CustomGeneralHandler extends ResponseEntityExceptionHandler {

    /**
     * Date Formatter for logging the timestamp when the Exception was thrown.
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Exception handler for : DatabaseFetchException
     * This handler will return a new ResponseEntity with the following parameters :
     * - HTTP Code : INTERNAL_SERVER_ERROR
     * - ERROR title : ex.getMessage()
     * - ERROR message : ex.getLocalizedMessage()
     *
     * @param ex the caught Exception
     * @return the Error to send
     */
    @ExceptionHandler(DatabaseFetchException.class)
    protected ResponseEntity<Object> handleDatabaseFetchException(DatabaseFetchException ex) {
        String date = formatter.format(new Date());
        ErrorResponse err = new ErrorResponse(date, ex.getMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler for : ResourceAlreadyExistsException
     * This handler will return a new ResponseEntity with the following parameters :
     * - HTTP Code : CONFLICT
     * - ERROR title : ex.getMessage()
     * - ERROR message : ex.getLocalizedMessage()
     *
     * @param ex the caught Exception
     * @return the Error to send
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    protected @NotNull ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        String date = formatter.format(new Date());
        ErrorResponse err = new ErrorResponse(date, ex.getMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for : ResourceNotFoundException
     * This handler will return a new ResponseEntity with the following parameters :
     * - HTTP Code : NOT_FOUND
     * - ERROR title : ex.getMessage()
     * - ERROR message : ex.getLocalizedMessage()
     *
     * @param ex the caught Exception
     * @return the Error to send
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    protected @NotNull ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String date = formatter.format(new Date());
        ErrorResponse err = new ErrorResponse(date, ex.getMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for : MethodArgumentNotValid
     * This handler will return a new ResponseEntity with the following parameters :
     * - HTTP Code : INTERNAL_SERVER_ERROR
     * - ERROR title : ex.getMessage()
     * - ERROR message : ex.getLocalizedMessage()
     *
     * @param ex the caught Exception
     * @return the Error to send
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<Map<String, String>> errorsList = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            Map<String, String> map = new HashMap<>();
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
            errorsList.add(map);
        }
        for (ObjectError objectError : globalErrors) {
            Map<String, String> map = new HashMap<>();
            map.put(objectError.getObjectName(), objectError.getDefaultMessage());
            errorsList.add(map);
        }

        String date = formatter.format(new Date());
        ErrorResponseArgs err = new ErrorResponseArgs(date, ex.getMessage(), errorsList);
        return new ResponseEntity<>(err, status);
    }

}
