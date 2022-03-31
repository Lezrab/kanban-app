package fr.istic.sir.kanban.aarzel.kanbanapp.services.cards;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanCardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.repository.IKanbanCardRepository;
import fr.istic.sir.kanban.aarzel.model.KanbanCardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Common methods for KanbanCard
 */
@Transactional
@Service
public class CommonKanbanCardsService {

    public static final String DATABASE_ERROR_MESSAGE = "Something went wrong while fetching the KanbanBoard from the BOARD database";
    public static final String KANBAN_CARD_NOT_FOUND_WITH_ID = "Kanban card not found with id : ";

    @Autowired
    private IKanbanCardRepository kanbanCardRepository;

    @Autowired
    private ConverterUtils converter;

    /**
     * Returns one KanbanCard by its id
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param id the id of the card
     * @return the card
     * @throws DatabaseFetchException if there is a database error
     */
    public Optional<KanbanCardEntity> getOneKanbanCardById(Long id) throws DatabaseFetchException {
        Optional<KanbanCardEntity> kanbanCardEntity;
        try {
            kanbanCardEntity = kanbanCardRepository.findById(id);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanCardEntity;
    }

    /**
     * Saves a KanbanCard in db
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param kanbanCardToSave the KanbanCard to save
     * @return the saved KanbanCard
     * @throws DatabaseFetchException if there is a database error
     */
    public KanbanCardEntity saveKanbanCard(KanbanCardEntity kanbanCardToSave) throws DatabaseFetchException {
        KanbanCardEntity kanbanCardEntity;
        try {
            kanbanCardEntity = kanbanCardRepository.save(kanbanCardToSave);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanCardEntity;
    }

    /**
     * Deletes a KanbanBoard by its id
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param id the id of the card
     * @throws DatabaseFetchException if there is a database error
     */
    public void deleteById(Long id) throws DatabaseFetchException {
        try {
            kanbanCardRepository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
    }

    /**
     * Converts a KanbanCardDTO to an entity, and save it.
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param kanbanCardDTO the DTO passed from the client to the server
     * @return the created KanbanCard in db
     * @throws DatabaseFetchException if there is a database error
     */
    public KanbanCardEntity convertToEntityAndSaveKanbanCard(KanbanCardDTO kanbanCardDTO) throws DatabaseFetchException {
        KanbanCardEntity kanbanCard = converter.convertOneKanbanCardDTOToEntity(kanbanCardDTO);
        return saveKanbanCard(kanbanCard);
    }
}
