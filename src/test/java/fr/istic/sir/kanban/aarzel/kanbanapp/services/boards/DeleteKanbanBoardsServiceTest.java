package fr.istic.sir.kanban.aarzel.kanbanapp.services.boards;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteKanbanBoardsServiceTest {
    @Mock
    private CommonKanbanBoardsService commonKanbanBoardsService;
    @Mock
    private Logger loggerMock;
    @InjectMocks
    private DeleteKanbanBoardsService deleteKanbanBoardsService;


    @Test
    @DisplayName("Should verify that the call to delete a KanbanBoard is made.")
    void testDeleteOneKanbanBoardDTO() throws DatabaseFetchException, ResourceNotFoundException {
        when(commonKanbanBoardsService.kanbanBoardFoundInDbId(any())).thenReturn(true);
        deleteKanbanBoardsService.deleteOneKanbanBoardByExternalId(1L, loggerMock);
        verify(commonKanbanBoardsService).kanbanBoardFoundInDbId(1L);
        verify(commonKanbanBoardsService).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testDeleteOneKanbanBoardDTO_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException {
        when(commonKanbanBoardsService.kanbanBoardFoundInDbId(any())).thenReturn(true);
        doThrow(new DatabaseFetchException("Something went wrong", new Exception())).when(commonKanbanBoardsService)
                .deleteById(1L);
        assertThrows(DatabaseFetchException.class,
                () -> deleteKanbanBoardsService.deleteOneKanbanBoardByExternalId(1L, loggerMock));
    }

    @Test
    @DisplayName("Should throw a ResourceNotFoundException.")
    void testDeleteOneKanbanBoardDTO_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        when(commonKanbanBoardsService.kanbanBoardFoundInDbId(any())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,
                () -> deleteKanbanBoardsService.deleteOneKanbanBoardByExternalId(1L, loggerMock));
    }

    @Test
    @DisplayName("Should verify that the call to delete each KanbanBoard is made.")
    void testDeleteManyKanbanBoardDTO() throws DatabaseFetchException {
        List<KanbanBoardEntity> entities = List.of(new KanbanBoardEntity(1L, "KanbanBoard", new ArrayList<>()),
                new KanbanBoardEntity(2L, "KanbanBoard", new ArrayList<>()));
        when(commonKanbanBoardsService.getAllKanbanBoardEntities()).thenReturn(entities);
        deleteKanbanBoardsService.deleteAllKanbanBoards(loggerMock);
        verify(commonKanbanBoardsService).deleteById(1L);
        verify(commonKanbanBoardsService).deleteById(2L);
    }
}
