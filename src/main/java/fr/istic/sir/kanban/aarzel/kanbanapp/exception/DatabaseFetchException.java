package fr.istic.sir.kanban.aarzel.kanbanapp.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception for handling Database errors.
 */
@Getter
@Setter
public class DatabaseFetchException extends Exception {
    public DatabaseFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
