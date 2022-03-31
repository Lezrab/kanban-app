package fr.istic.sir.kanban.aarzel.kanbanapp.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception for handling Resource that couldn't be found.
 */
@Getter
@Setter
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
