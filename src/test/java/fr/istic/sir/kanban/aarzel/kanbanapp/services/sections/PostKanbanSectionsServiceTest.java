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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostKanbanSectionsServiceTest {
    @Mock
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Mock
    private CommonKanbanSectionsService commonKanbanSectionsService;

    @Mock
    private Logger loggerMock;

    @Mock
    private ConverterUtils converter;

    @InjectMocks
    private PostKanbanSectionsService postKanbanSectionsService;


    @Test
    @DisplayName("Should create a section in the board.")
    void testCreateOneKanbanSectionInKanbanBoard() throws DatabaseFetchException, ResourceNotFoundException, ResourceAlreadyExistsException {
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        KanbanSectionEntity savedSection = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanSectionDTO dtoToSave = new KanbanSectionDTO().label("SectionLabel").hexColor("#000000").position(1L);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.kanbanSectionFoundInDbByLabel(dtoToSave.getLabel())).thenReturn(false);
        when(commonKanbanSectionsService.kanbanSectionFoundInDbByPosition(dtoToSave.getPosition())).thenReturn(false);

        when(commonKanbanSectionsService.convertToEntityAndSaveKanbanSection(dtoToSave)).thenReturn(savedSection);
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(converter.convertOneKanbanSectionEntityToDTO(savedSection)).thenReturn(dtoToSave);
        KanbanSectionDTO savedDto = postKanbanSectionsService.createOneKanbanSectionInKanbanBoard(1L, dtoToSave, loggerMock);
        assertNotNull(savedDto);
        assertEquals("SectionLabel", savedDto.getLabel());
        assertEquals("#000000", savedDto.getHexColor());
        assertEquals(1L, savedDto.getPosition());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testCreateOneKanbanSectionInKanbanBoard_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.kanbanSectionFoundInDbByPosition(any())).thenReturn(Boolean.TRUE);
        when(commonKanbanSectionsService.kanbanSectionFoundInDbByLabel(any())).thenReturn(Boolean.FALSE);
        ResourceAlreadyExistsException thrown = assertThrows(ResourceAlreadyExistsException.class, () ->
                        postKanbanSectionsService.createOneKanbanSectionInKanbanBoard(1L, new KanbanSectionDTO(), loggerMock),
                "");
        assertEquals("Kanban section already exists in database with id : null", thrown.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        postKanbanSectionsService.createOneKanbanSectionInKanbanBoard(1L, new KanbanSectionDTO(), loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown2.getMessage());
    }
}

