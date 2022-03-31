package fr.istic.sir.kanban.aarzel.kanbanapp.services.cards;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanCardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.repository.IKanbanCardRepository;
import fr.istic.sir.kanban.aarzel.model.KanbanCardDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommonKanbanCardsServiceTest {
    @Mock
    private IKanbanCardRepository kanbanCardRepository;
    @Mock
    private ConverterUtils converter;
    @InjectMocks
    private CommonKanbanCardsService commonKanbanCardsService;

    private final KanbanCardEntity card = new KanbanCardEntity(1L, "CardLabel", "CardDescription", LocalDate.now(), 60, "Url", "Me", List.of("TODO"));
    private final KanbanCardDTO cardDto = new KanbanCardDTO().label("cardLabel");

    @Test
    @DisplayName("Should return the KanbanCardEntity (by id).")
    void testGetOneKanbanCardById() throws DatabaseFetchException {
        when(kanbanCardRepository.findById(1L)).thenReturn(Optional.of(card));
        Optional<KanbanCardEntity> result = commonKanbanCardsService.getOneKanbanCardById(1L);
        assertNotNull(result);
        result.ifPresent(kanbanCardEntity -> assertEquals("CardLabel", kanbanCardEntity.getLabel()));
        verify(kanbanCardRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testGetOneKanbanCardById_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanCardRepository)
                .findById(1L);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanCardsService.getOneKanbanCardById(1L));
    }

    @Test
    @DisplayName("Should save the KanbanCard.")
    void testSaveKanbanCard() throws DatabaseFetchException {
        when(kanbanCardRepository.save(card)).thenReturn(card);
        KanbanCardEntity result = commonKanbanCardsService.saveKanbanCard(card);
        assertEquals(card, result);
        verify(kanbanCardRepository).save(card);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testSaveKanbanCard_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanCardRepository)
                .save(card);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanCardsService.saveKanbanCard(card));
    }


    @Test
    @DisplayName("Should verify that the call to the repository is made.")
    void testDeleteById() throws DatabaseFetchException {
        commonKanbanCardsService.deleteById(1L);
        verify(kanbanCardRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testDeleteById_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanCardRepository)
                .deleteById(1L);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanCardsService.deleteById(1L));
    }

    @Test
    @DisplayName("Should call the repository method.")
    void testConvertToEntityAndSaveKanbanCard() throws DatabaseFetchException {
        when(converter.convertOneKanbanCardDTOToEntity(cardDto)).thenReturn(card);
        when(kanbanCardRepository.save(card)).thenReturn(card);
        KanbanCardEntity result = commonKanbanCardsService.convertToEntityAndSaveKanbanCard(cardDto);
        assertNotNull(result);
        verify(kanbanCardRepository).save(any());
    }
}

