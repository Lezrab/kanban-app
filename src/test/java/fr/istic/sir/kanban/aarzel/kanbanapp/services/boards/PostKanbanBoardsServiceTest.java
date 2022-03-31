package fr.istic.sir.kanban.aarzel.kanbanapp.services.boards;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostKanbanBoardsServiceTest {
    @Mock
    private CommonKanbanBoardsService commonKanbanBoardsService;
    @Mock
    private ConverterUtils converter;
    @Mock
    private Logger loggerMock;
    @InjectMocks
    private PostKanbanBoardsService postKanbanBoardsService;

    private final KanbanBoardDTO dtoToSave = new KanbanBoardDTO().label("MyBoard");

    @Test
    @DisplayName("Should return the created KanbanBoard.")
    void testCreateOneKanbanBoardById() throws DatabaseFetchException {
        KanbanBoardEntity entityFromDto = converter.convertOneKanbanBoardDTOToEntity(dtoToSave);
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(commonKanbanBoardsService.convertToEntityAndSaveKanbanBoard(dtoToSave)).thenReturn(entityFromDto);
        when(converter.convertOneKanbanBoardEntityToDTO(entityFromDto)).thenReturn(dtoToSave);
        KanbanBoardDTO result = postKanbanBoardsService.createOneKanbanBoardById(1L, dtoToSave, loggerMock);
        Assertions.assertEquals(dtoToSave, result);
    }
}