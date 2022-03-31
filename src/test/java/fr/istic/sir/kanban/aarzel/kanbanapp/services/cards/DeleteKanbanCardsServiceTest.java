package fr.istic.sir.kanban.aarzel.kanbanapp.services.cards;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanCardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.TestUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.sections.CommonKanbanSectionsService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteKanbanCardsServiceTest {

    @Mock
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Mock
    private CommonKanbanSectionsService commonKanbanSectionsService;

    @Mock
    private CommonKanbanCardsService commonKanbanCardsService;

    @Mock
    private Logger loggerMock;

    @InjectMocks
    private DeleteKanbanCardsService deleteKanbanCardsService;

    @Test
    @DisplayName("Should delete card from section.")
    void testDeleteOneKanbanCardByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanCardEntity card = TestUtils.nominalKanbanCardEntity(1L);
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        card.getAssociatedTags().add("TODO");
        section.getKanbanCards().add(card);
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        when(commonKanbanCardsService.getOneKanbanCardById(1L)).thenReturn(Optional.of(card));
        assertEquals(1, section.getKanbanCards().size());
        deleteKanbanCardsService.deleteOneKanbanCardByKanbanSection(1L, 1L, 1L, loggerMock);
        assertEquals(0, section.getKanbanCards().size());
        verify(commonKanbanCardsService).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testDeleteOneKanbanCardByKanbanSection_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        when(commonKanbanCardsService.getOneKanbanCardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        deleteKanbanCardsService.deleteOneKanbanCardByKanbanSection(1L, 1L, 1L, loggerMock),
                "");
        assertEquals("Kanban card not found with id : 1", thrown.getMessage());
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        deleteKanbanCardsService.deleteOneKanbanCardByKanbanSection(1L, 1L, 1L, loggerMock),
                "");
        assertEquals("Kanban section not found with id : 1", thrown2.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown3 = assertThrows(ResourceNotFoundException.class, () ->
                        deleteKanbanCardsService.deleteOneKanbanCardByKanbanSection(1L, 1L, 1L, loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown3.getMessage());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testDeleteKanbanCardsByKanbanSection_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        deleteKanbanCardsService.deleteKanbanCardsByKanbanSection(1L, 1L, loggerMock),
                "");
        assertEquals("Kanban section not found with id : 1", thrown.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        deleteKanbanCardsService.deleteKanbanCardsByKanbanSection(1L, 1L, loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown2.getMessage());
    }

    @Test
    @DisplayName("Should delete cards from section.")
    void testDeleteKanbanCardsByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanCardEntity card = TestUtils.nominalKanbanCardEntity(1L);
        KanbanCardEntity card2 = TestUtils.nominalKanbanCardEntity(2L);
        KanbanCardEntity card3 = TestUtils.nominalKanbanCardEntity(3L);
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        card.getAssociatedTags().add("TODO");
        section.getKanbanCards().addAll(List.of(card, card2, card3));
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        assertEquals(3, section.getKanbanCards().size());
        deleteKanbanCardsService.deleteKanbanCardsByKanbanSection(1L, 1L, loggerMock);
        assertTrue(section.getKanbanCards().isEmpty());
        verify(commonKanbanCardsService, times(3)).deleteById(any());
    }
}
