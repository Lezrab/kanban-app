package fr.istic.sir.kanban.aarzel.kanbanapp.handler;

import fr.istic.sir.kanban.aarzel.kanbanapp.enums.ECustomErrorCode;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ErrorResponse;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ErrorResponseForParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class CustomGeneralHandler extends ResponseEntityExceptionHandler {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @ExceptionHandler(DatabaseFetchException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(DatabaseFetchException ex, HttpServletRequest request) {
        return new ErrorResponse(ECustomErrorCode.DATABASE_FETCHIN_ERROR, )
        String uri = ((ServletWebRequest) request).getRequest().getRequestURL().toString();
        String method = Objects.requireNonNull(((ServletWebRequest) request).getHttpMethod()).toString();
        Date date = new Date();
        ErrorResponse error = new ErrorResponse(FORMATTER.format(date), method, uri, HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        String uri = ((ServletWebRequest) request).getRequest().getRequestURL().toString();
        String method = Objects.requireNonNull(((ServletWebRequest) request).getHttpMethod()).toString();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        Date date = new Date();
        ErrorResponse errorResponse = new ErrorResponse(FORMATTER.format(date), method, uri, HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getHttpStatus());
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        String uri = ((ServletWebRequest) request).getRequest().getRequestURL().toString();
        String method = Objects.requireNonNull(((ServletWebRequest) request).getHttpMethod()).toString();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        ArrayList<Map<String, String>> errorsList = new ArrayList<>();
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

        Date date = new Date();
        ErrorResponseForParam errorResponse = new ErrorResponseForParam(FORMATTER.format(date), method, uri, HttpStatus.BAD_REQUEST, errorsList);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getHttpStatus());
    }

}
