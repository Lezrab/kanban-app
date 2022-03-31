package fr.istic.sir.kanban.aarzel.kanbanapp.services.sections;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.TestUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.cards.CommonKanbanCardsService;
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
class DeleteKanbanSectionsServiceTest {
    @Mock
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Mock
    private CommonKanbanSectionsService commonKanbanSectionsService;

    @Mock
    private CommonKanbanCardsService commonKanbanCardsService;

    @Mock
    private Logger loggerMock;

    @InjectMocks
    private DeleteKanbanSectionsService deleteKanbanSectionsService;

    @Test
    @DisplayName("Should delete section from board.")
    void testDeleteOneKanbanSectionInKanbanBoard() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        assertEquals(1, board.getKanbanSections().size());
        deleteKanbanSectionsService.deleteOneKanbanSectionInKanbanBoard(1L, 1L, loggerMock);
        assertEquals(0, board.getKanbanSections().size());
        verify(commonKanbanSectionsService).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testDeleteOneKanbanSectionInKanbanBoard_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        deleteKanbanSectionsService.deleteOneKanbanSectionInKanbanBoard(1L, 1L, loggerMock),
                "");
        assertEquals("Kanban section not found with id : 1", thrown.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        deleteKanbanSectionsService.deleteOneKanbanSectionInKanbanBoard(1L, 1L, loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown2.getMessage());
    }

    @Test
    @DisplayName("Should delete sections from board.")
    void testDeleteKanbanSectionsByKanbanBoard() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanSectionEntity section2 = TestUtils.nominalKanbanSectionEntity(2L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        board.getKanbanSections().addAll(List.of(section, section2));
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        assertEquals(2, board.getKanbanSections().size());
        deleteKanbanSectionsService.deleteKanbanSectionsByKanbanBoard(1L, loggerMock);
        assertTrue(board.getKanbanSections().isEmpty());
        verify(commonKanbanSectionsService, times(2)).deleteById(any());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testDeleteKanbanSectionsByKanbanBoard_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        deleteKanbanSectionsService.deleteKanbanSectionsByKanbanBoard(1L, loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown.getMessage());
    }
}

