package fr.istic.sir.kanban.aarzel.kanbanapp.services.sections;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.TestUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.cards.CommonKanbanCardsService;
import fr.istic.sir.kanban.aarzel.model.KanbanSectionDTO;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetKanbanSectionsServiceTest {
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
    private GetKanbanSectionsService getKanbanSectionsService;

    @Test
    @DisplayName("Should return the section.")
    void testGetKanbanSectionByKanbanBoard() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(converter.convertOneKanbanSectionEntityToDTO(section)).thenReturn(new KanbanSectionDTO().label("SectionLabel"));
        KanbanSectionDTO result = getKanbanSectionsService.getKanbanSectionByKanbanBoard(1L, 1L, loggerMock);
        assertEquals("SectionLabel", result.getLabel());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testGetKanbanSectionByKanbanBoard_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException{
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        getKanbanSectionsService.getKanbanSectionByKanbanBoard(1L, 1L, loggerMock),
                "");
        assertEquals("Kanban section not found with id : 1", thrown.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        getKanbanSectionsService.getKanbanSectionByKanbanBoard(1L, 1L, loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown2.getMessage());
    }

    @Test
    @DisplayName("Should return the sections.")
    void testGetKanbanCardsByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanSectionEntity section2 = TestUtils.nominalKanbanSectionEntity(2L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        board.getKanbanSections().add(section);
        board.getKanbanSections().add(section2);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(converter.convertOneKanbanSectionEntityToDTO(section)).thenReturn(new KanbanSectionDTO().label("SectionLabel"));
        when(converter.convertOneKanbanSectionEntityToDTO(section2)).thenReturn(new KanbanSectionDTO().label("SectionLabel2"));
        List<KanbanSectionDTO> results = getKanbanSectionsService.getKanbanSectionsByKanbanBoard(1L, loggerMock);
        assertEquals(2, results.size());
        assertEquals("SectionLabel", results.get(0).getLabel());
        assertEquals("SectionLabel2", results.get(1).getLabel());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testGetKanbanCardsByKanbanSection_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        getKanbanSectionsService.getKanbanSectionsByKanbanBoard(1L, loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown2.getMessage());
    }
}

