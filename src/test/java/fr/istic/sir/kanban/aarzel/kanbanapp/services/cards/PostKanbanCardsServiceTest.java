package fr.istic.sir.kanban.aarzel.kanbanapp.services.cards;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanCardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.TestUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.sections.CommonKanbanSectionsService;
import fr.istic.sir.kanban.aarzel.model.KanbanCardDTO;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostKanbanCardsServiceTest {

    @Mock
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Mock
    private CommonKanbanSectionsService commonKanbanSectionsService;

    @Mock
    private CommonKanbanCardsService commonKanbanCardsService;

    @Mock
    private Logger loggerMock;

    @Mock
    private ConverterUtils converter;

    @InjectMocks
    private PostKanbanCardsService postKanbanCardsService;

    @Test
    @DisplayName("Should create a card in the section.")
    void testCreateOneKanbanCardByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        KanbanCardDTO dtoToSave = new KanbanCardDTO().label("MyCard").affectedUser("Me").description("CardDescription").addAssociatedTagsItem("TODO");
        KanbanCardEntity savedEntity = new KanbanCardEntity(1L, "MyCard", "CardDescription", null, 0, null, "Me", List.of("TODO"));
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        when(commonKanbanCardsService.convertToEntityAndSaveKanbanCard(dtoToSave)).thenReturn(savedEntity);
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(converter.convertOneKanbanCardEntityToDTO(savedEntity)).thenReturn(dtoToSave);
        KanbanCardDTO savedDto = postKanbanCardsService.createOneKanbanCardByKanbanSection(1L, 1L, 1L, dtoToSave, loggerMock);
        assertNotNull(savedDto);
        assertEquals("MyCard", savedDto.getLabel());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testCreateOneKanbanCardByKanbanSection_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        postKanbanCardsService.createOneKanbanCardByKanbanSection(1L, 1L, 1L, new KanbanCardDTO(), loggerMock),
                "");
        assertEquals("Kanban section not found with id : 1", thrown.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        postKanbanCardsService.createOneKanbanCardByKanbanSection(1L, 1L, 1L, new KanbanCardDTO(), loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown2.getMessage());
    }
}
