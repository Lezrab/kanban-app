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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetKanbanBoardsServiceTest {
    @Mock
    private CommonKanbanBoardsService commonKanbanBoardsService;
    @Mock
    private ConverterUtils converter;
    @Mock
    private Logger loggerMock;
    @InjectMocks
    private GetKanbanBoardsService getKanbanBoardsService;

    private final KanbanBoardEntity kanbanBoardEntity = new KanbanBoardEntity(1L, "MyBoard", new ArrayList<>());
    private final KanbanBoardEntity kanbanBoardEntity2 = new KanbanBoardEntity(2L, "MyBoard2", new ArrayList<>());


    @Test
    @DisplayName("Should return the KanbanBoard DTO.")
    void testGetOneKanbanBoardById() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanBoardDTO expected = new KanbanBoardDTO().label("MyBoard");
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(kanbanBoardEntity));
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(converter.convertOneKanbanBoardEntityToDTO(kanbanBoardEntity)).thenReturn(expected);
        KanbanBoardDTO result = getKanbanBoardsService.getOneKanbanBoardById(1L, loggerMock);
        assertEquals(converter.convertOneKanbanBoardEntityToDTO(kanbanBoardEntity), result);
        assertNotNull(expected);
        assertNotNull(result);
        assertEquals(expected, result);
        verify(converter, times(2)).convertOneKanbanBoardEntityToDTO(kanbanBoardEntity);
        verify(commonKanbanBoardsService).getOneKanbanBoardById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testGetOneKanbanBoardById_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> getKanbanBoardsService.getOneKanbanBoardById(1L, loggerMock));
    }

    @Test
    @DisplayName("Should return the KanbanBoard DTOs.")
    void testGetManyKanbanBoards() throws DatabaseFetchException {
        KanbanBoardDTO expected = new KanbanBoardDTO().label("MyBoard");
        KanbanBoardDTO expected2 = new KanbanBoardDTO().label("MyBoard2");
        List<KanbanBoardEntity> entities = List.of(kanbanBoardEntity, kanbanBoardEntity2);
        when(commonKanbanBoardsService.getAllKanbanBoardEntities()).thenReturn(entities);
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(converter.convertOneKanbanBoardEntityToDTO(entities.get(0))).thenReturn(expected);
        when(converter.convertOneKanbanBoardEntityToDTO(entities.get(1))).thenReturn(expected2);
        List<KanbanBoardDTO> result = getKanbanBoardsService.getManyKanbanBoards(loggerMock);
        assertEquals(2, result.size());
        assertEquals(expected, result.get(0));
        assertEquals(expected2, result.get(1));
    }
}