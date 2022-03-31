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
class UpdateKanbanCardsServiceTest {
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
    private UpdateKanbanCardsService updateKanbanCardsService;

    @Test
    @DisplayName("Should update the card with new informations.")
    void testUpdateOneKanbanCardByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        KanbanCardEntity card = TestUtils.nominalKanbanCardEntity(1L);
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        KanbanCardDTO updatedCardDto = new KanbanCardDTO().label("CardLabelUpdated").description("CardDescriptionUpdated").creationDate(card.getCreationDate())
                        .affectedUser("CardUserUpdated").associatedUrl("CardUrlUpdated").estimatedTime(120).associatedTags(List.of("TODO"));
        KanbanCardEntity updatedCardEntity = new KanbanCardEntity(1L, "CardLabelUpdated", "CardDescriptionUpdated",
                updatedCardDto.getCreationDate(), 120, "CardUrlUpdated", "CardUserUpdated", List.of("TODO"));
        section.getKanbanCards().add(card);
        board.getKanbanSections().add(section);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        when(commonKanbanCardsService.getOneKanbanCardById(1L)).thenReturn(Optional.of(card));
        when(commonKanbanBoardsService.getConverter()).thenReturn(converter);
        when(converter.convertOneKanbanCardDTOToEntity(updatedCardDto)).thenReturn(updatedCardEntity);
        when(commonKanbanCardsService.saveKanbanCard(updatedCardEntity)).thenReturn(updatedCardEntity);
        when(converter.convertOneKanbanCardEntityToDTO(updatedCardEntity)).thenReturn(updatedCardDto);
        KanbanCardDTO result = updateKanbanCardsService.updateOneKanbanCardByKanbanSection(1L, 1L, 1L, updatedCardDto, loggerMock);
        assertNotNull(result);
        assertEquals("CardLabelUpdated", result.getLabel());
        assertEquals(120, result.getEstimatedTime());
        assertEquals("TODO", result.getAssociatedTags().get(0));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException.")
    void testUpdateOneKanbanCardByKanbanSection_RESOURCE_NOT_FOUND_EXCEPTION() throws DatabaseFetchException {
        KanbanBoardEntity board = TestUtils.nominalKanbanBoardEntity(1L);
        KanbanSectionEntity section = TestUtils.nominalKanbanSectionEntity(1L);
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.of(section));
        when(commonKanbanCardsService.getOneKanbanCardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrownCard = assertThrows(ResourceNotFoundException.class, () ->
                        updateKanbanCardsService.updateOneKanbanCardByKanbanSection(1L, 1L, 1L, new KanbanCardDTO(), loggerMock),
                "");
        assertEquals("Kanban card not found with id : 1", thrownCard.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.of(board));
        when(commonKanbanSectionsService.getOneKanbanSectionById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        updateKanbanCardsService.updateOneKanbanCardByKanbanSection(1L, 1L, 1L, new KanbanCardDTO(), loggerMock),
                "");
        assertEquals("Kanban section not found with id : 1", thrown.getMessage());
        when(commonKanbanBoardsService.getOneKanbanBoardById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown2 = assertThrows(ResourceNotFoundException.class, () ->
                        updateKanbanCardsService.updateOneKanbanCardByKanbanSection(1L, 1L, 1L, new KanbanCardDTO(), loggerMock),
                "");
        assertEquals("Kanban board not found with id : 1", thrown2.getMessage());
    }
}
