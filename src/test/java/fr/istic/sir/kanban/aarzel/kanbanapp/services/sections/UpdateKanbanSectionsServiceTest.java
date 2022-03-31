package fr.istic.sir.kanban.aarzel.kanbanapp.services.sections;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceAlreadyExistsException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.TestUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.model.KanbanSectionDTO;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateKanbanSectionsServiceTest {
    @Mock
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Mock
    private CommonKanbanSectionsService commonKanbanSectionsService;

    @Mock
    private Logger loggerMock;

    @Mock
    private ConverterUtils converter;

    @InjectMocks
    private UpdateKanbanSectionsService updateKanbanSectionsService;

    @Test
    @DisplayName("Should update the section with new informations.")
    void testUpdateOneKanbanSectionInKanbanBoard() throws DatabaseFetchException, ResourceNotFoundException, ResourceAlreadyExistsException {
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanSectionDTO updatedSectionDto = new KanbanSectionDTO().label("SectionLabelUpdated").hexColor("#ABCDEF").position(3L);
        KanbanSectionEntity updatedSectionEntity = new KanbanSectionEntity(1L, "SectionLabelUpdated", "#ABCDEF", 3L, new ArrayList<>());
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(converter.convertOneKanbanSectionDTOToEntity(updatedSectionDto)).thenReturn(updatedSectionEntity);
        when(commonKanbanSectionsService.saveKanbanSection(updatedSectionEntity)).thenReturn(updatedSectionEntity);
        when(converter.convertOneKanbanSectionEntityToDTO(updatedSectionEntity)).thenReturn(updatedSectionDto);
        KanbanSectionDTO result = updateKanbanSectionsService.updateOneKanbanSectionInKanbanBoard(1L, 1L, updatedSectionDto, loggerMock);
        assertNotNull(result);
        assertEquals("SectionLabelUpdated", result.getLabel());
        assertEquals("#ABCDEF", result.getHexColor());
        assertEquals(3L, result.getPosition());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testUpdateOneKanbanSectionInKanbanBoard_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        updateKanbanSectionsService.updateOneKanbanSectionInKanbanBoard(1L, 1L, new KanbanSectionDTO(), loggerMock),
                "");
        assertEquals("Kanban section not found with id : 1", thrown.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        updateKanbanSectionsService.updateOneKanbanSectionInKanbanBoard(1L, 1L, new KanbanSectionDTO(), loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown2.getMessage());
    }
}

