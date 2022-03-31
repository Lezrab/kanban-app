package fr.istic.sir.kanban.aarzel.kanbanapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Custom error that can be returned by the API.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String timestamp;
    private String title;
    private String message;
}
