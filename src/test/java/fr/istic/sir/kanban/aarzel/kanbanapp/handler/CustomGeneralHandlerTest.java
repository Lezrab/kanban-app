package fr.istic.sir.kanban.aarzel.kanbanapp.handler;

import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceAlreadyExistsException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class CustomGeneralHandlerTest {
    @Mock
    private SimpleDateFormat FORMATTER;
    @Mock
    private Log pageNotFoundLogger;
    @Mock
    private Log logger;
    @InjectMocks
    private CustomGeneralHandler customGeneralHandler;

    @Test
    @DisplayName("Should verify that the exception contains a String.")
    void testHandleDatabaseFetchException() {
        DatabaseFetchException exception = new DatabaseFetchException("Error with the database connection",
                new Exception());
        ResponseEntity<Object> result = customGeneralHandler.handleDatabaseFetchException(exception);
        assertEquals(INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().toString().contains("Error with the database connection"));
    }

    @Test
    @DisplayName("Should verify that the exception contains a String.")
    void testHandleResourceAlreadyExistsException() {
        ResponseEntity<Object> result = customGeneralHandler.handleResourceAlreadyExistsException(new ResourceAlreadyExistsException("errorMessage"));
        assertEquals(CONFLICT, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().toString().contains("errorMessage"));
    }

    @Test
    @DisplayName("Should verify that the exception contains a String.")
    void testHandleResourceNotFoundException() {

        ResponseEntity<Object> result = customGeneralHandler.handleResourceNotFoundException(new ResourceNotFoundException("notFound"));
        assertEquals(NOT_FOUND, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().toString().contains("notFound"));
    }
}