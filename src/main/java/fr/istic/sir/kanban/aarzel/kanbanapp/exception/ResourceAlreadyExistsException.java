package fr.istic.sir.kanban.aarzel.kanbanapp.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception for handling Resource that already exists.
 */
@Getter
@Setter
public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
