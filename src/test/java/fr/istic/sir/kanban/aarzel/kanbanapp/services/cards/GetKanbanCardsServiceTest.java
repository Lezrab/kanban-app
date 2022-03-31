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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetKanbanCardsServiceTest {

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
    private GetKanbanCardsService getKanbanCardsService;

    @Test
    @DisplayName("Should return the card.")
    void testGetOneKanbanCardByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanCardEntity card = TestUtils.nominalKanbanCardEntity(1L);
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        card.getAssociatedTags().add("TODO");
        section.getKanbanCards().add(card);
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        when(commonKanbanCardsService.getOneKanbanCardById(1L)).thenReturn(Optional.of(card));
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(converter.convertOneKanbanCardEntityToDTO(card)).thenReturn(new KanbanCardDTO().label("CardLabel"));
        KanbanCardDTO result = getKanbanCardsService.getOneKanbanCardByKanbanSection(1L, 1L, 1L, loggerMock);
        assertEquals("CardLabel", result.getLabel());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testGetOneKanbanCardByKanbanSection_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException{
        KanbanCardEntity card = TestUtils.nominalKanbanCardEntity(1L);
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        card.getAssociatedTags().add("TODO");
        section.getKanbanCards().add(card);
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        when(commonKanbanCardsService.getOneKanbanCardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        getKanbanCardsService.getOneKanbanCardByKanbanSection(1L, 1L, 1L, loggerMock),
                "");
        assertEquals("Kanban card not found with id : 1", thrown.getMessage());
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        getKanbanCardsService.getOneKanbanCardByKanbanSection(1L, 1L, 1L, loggerMock),
                "");
        assertEquals("Kanban section not found with id : 1", thrown2.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown3 = assertThrows(ResourceNotFoundException.class, () ->
                        getKanbanCardsService.getOneKanbanCardByKanbanSection(1L, 1L, 1L, loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown3.getMessage());
    }

    @Test
    @DisplayName("Should return the cards.")
    void testGetKanbanCardsByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanCardEntity card = TestUtils.nominalKanbanCardEntity(1L);
        KanbanCardEntity card2 = TestUtils.nominalKanbanCardEntity(2L);
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        card.getAssociatedTags().add("TODO");
        section.getKanbanCards().add(card);
        section.getKanbanCards().add(card2);
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(converter.convertOneKanbanCardEntityToDTO(card)).thenReturn(new KanbanCardDTO().label("CardLabel"));
        when(converter.convertOneKanbanCardEntityToDTO(card2)).thenReturn(new KanbanCardDTO().label("CardLabel2"));
        List<KanbanCardDTO> results = getKanbanCardsService.getKanbanCardsByKanbanSection(1L, 1L, loggerMock);
        assertEquals(2, results.size());
        assertEquals("CardLabel", results.get(0).getLabel());
        assertEquals("CardLabel2", results.get(1).getLabel());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testGetKanbanCardsByKanbanSection_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        KanbanCardEntity card = TestUtils.nominalKanbanCardEntity(1L);
        KanbanCardEntity card2 = TestUtils.nominalKanbanCardEntity(2L);
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        card.getAssociatedTags().add("TODO");
        section.getKanbanCards().add(card);
        section.getKanbanCards().add(card2);
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        getKanbanCardsService.getKanbanCardsByKanbanSection(1L, 1L, loggerMock),
                "");
        assertEquals("Kanban section not found with id : 1", thrown.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        getKanbanCardsService.getKanbanCardsByKanbanSection(1L, 1L, loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown2.getMessage());
    }
}
