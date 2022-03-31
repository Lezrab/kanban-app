package fr.istic.sir.kanban.aarzel.kanbanapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Custom error that can be returned by the API, including a list of missing parameters.
 */
@Data
@AllArgsConstructor
public class ErrorResponseArgs {
    private String timestamp;
    private String title;
    private List<Map<String, String>> paramErrors;

}
