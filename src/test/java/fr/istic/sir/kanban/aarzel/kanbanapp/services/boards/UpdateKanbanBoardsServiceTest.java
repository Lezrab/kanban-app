package fr.istic.sir.kanban.aarzel.kanbanapp.services.boards;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateKanbanBoardsServiceTest {
    @Mock
    private CommonKanbanBoardsService commonKanbanBoardsService;
    @Mock
    private ConverterUtils converter;
    @Mock
    private Logger loggerMock;
    @InjectMocks
    private UpdateKanbanBoardsService updateKanbanBoardsService;

    private final KanbanBoardDTO dtoToSave = new KanbanBoardDTO().label("MyBoardUpdated");


    @Test
    @DisplayName("Should update the KanbanBoard.")
    void testPutOneKanbanBoardByExternalId() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanBoardEntity entityFromDb = new KanbanBoardEntity(1L, "MyBoard", new ArrayList<>());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(entityFromDb));
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        KanbanBoardEntity entityUpdated = new KanbanBoardEntity(1L, "MyBoardUpdated", new ArrayList<>());
        when(converter.convertOneKanbanBoardDTOToEntity(dtoToSave)).thenReturn(entityUpdated);
        when(commonKanbanBoardsService.saveKanbanBoard(entityUpdated)).thenReturn(entityUpdated);
        when(converter.convertOneKanbanBoardEntityToDTO(entityUpdated)).thenReturn(dtoToSave);
        KanbanBoardDTO result = updateKanbanBoardsService.putOneKanbanBoardByExternalId(1L, dtoToSave, loggerMock);
        assertEquals(dtoToSave, result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testPutOneKanbanBoardByExternalId_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> updateKanbanBoardsService.putOneKanbanBoardByExternalId(1L, dtoToSave, loggerMock));
    }
}