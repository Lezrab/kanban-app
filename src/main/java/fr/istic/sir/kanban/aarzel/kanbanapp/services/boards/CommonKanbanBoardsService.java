package fr.istic.sir.kanban.aarzel.kanbanapp.services.boards;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.repository.IKanbanBoardRepository;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Common methods for KanbanBoard
 */
@Service
@Getter
public class CommonKanbanBoardsService {

    @Autowired
    private IKanbanBoardRepository kanbanBoardRepository;

    @Autowired
    private ConverterUtils converter;

    public static final String DATABASE_ERROR_MESSAGE = "Something went wrong while fetching the KanbanBoard from the BOARD database";
    public static final String KANBAN_BOARD_NOT_FOUND_WITH_ID = "Kanban board not found with id : ";
    public static final String KANBAN_BOARD_ALREADY_EXISTS = "Kanban board already exists in database with id : ";

    /**
     * Returns one KanbanBoard by its id
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param id the id of the board
     * @return the board
     * @throws DatabaseFetchException if there is a database error
     */
    public Optional<KanbanBoardEntity> getOneKanbanBoardById(Long id) throws DatabaseFetchException {
        Optional<KanbanBoardEntity> kanbanBoardEntity;
        try {
            kanbanBoardEntity = kanbanBoardRepository.findById(id);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanBoardEntity;
    }

    /**
     * Returns one KanbanBoard by its label
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param label the label of the board
     * @return the board
     * @throws DatabaseFetchException if there is a database error
     */
    public Optional<KanbanBoardEntity> getOneKanbanBoardByLabel(String label) throws DatabaseFetchException {
        Optional<KanbanBoardEntity> kanbanBoardEntity;
        try {
            kanbanBoardEntity = kanbanBoardRepository.findByLabel(label);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanBoardEntity;
    }

    /**
     * Checks if a KanbanBoard exist in db by id
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param id the id of the board
     * @return true/false
     * @throws DatabaseFetchException if there is a database error
     */
    public Boolean kanbanBoardFoundInDbId(Long id) throws DatabaseFetchException {
        boolean kanbanBoardExists;
        try {
            kanbanBoardExists = kanbanBoardRepository.existsById(id);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanBoardExists;
    }

    /**
     * Checks if a KanbanBoard exist in db by label
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param label the label of te board
     * @return true/false
     * @throws DatabaseFetchException if there is a database error
     */
    public Boolean kanbanBoardFoundInDbByLabel(String label) throws DatabaseFetchException {
        Boolean kanbanBoardExists;
        try {
            kanbanBoardExists = kanbanBoardRepository.existsByLabel(label);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanBoardExists;
    }

    /**
     * Returns all KanbanBoards in db
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @return all the KanbanBoards
     * @throws DatabaseFetchException if there is a database error
     */
    public List<KanbanBoardEntity> getAllKanbanBoardEntities() throws DatabaseFetchException {
        List<KanbanBoardEntity> existingKanbanBoardEntites;
        try {
            existingKanbanBoardEntites = kanbanBoardRepository.findAll();
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return existingKanbanBoardEntites;
    }

    /**
     * Deletes a KanbanBoard by its id
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param id the id of the board
     * @throws DatabaseFetchException if there is a database error
     */
    public void deleteById(Long id) throws DatabaseFetchException {
        try {
            kanbanBoardRepository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
    }

    /**
     * Saves a KanbanBoard in db
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param kanbanBoardEntityToSave the KanbanBoard to save
     * @return the saved KanbanBoard
     * @throws DatabaseFetchException if there is a database error
     */
    public KanbanBoardEntity saveKanbanBoard(KanbanBoardEntity kanbanBoardEntityToSave) throws DatabaseFetchException {
        KanbanBoardEntity kanbanBoardEntity;
        try {
            kanbanBoardEntity = kanbanBoardRepository.save(kanbanBoardEntityToSave);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanBoardEntity;
    }

    /**
     * Converts a KanbanBoardDTO to an entity, and adds 3 sections to it.
     * Every time we create a KanbanBoard, it will be initialised with 3 KanbanSections.
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param kanbanBoardDTO the DTO passed from the client to the server
     * @return the created KanbanBoard in db
     * @throws DatabaseFetchException if there is a database error
     */
    public KanbanBoardEntity convertToEntityAndSaveKanbanBoard(KanbanBoardDTO kanbanBoardDTO) throws DatabaseFetchException {
        KanbanBoardEntity kanbanBoardEntity = converter.convertOneKanbanBoardDTOToEntity(kanbanBoardDTO);
        kanbanBoardEntity.addSection(new KanbanSectionEntity(null, "En attente", "#e74c3c", 1L, new ArrayList<>()));
        kanbanBoardEntity.addSection(new KanbanSectionEntity(null, "En cours", "#e67e22", 2L, new ArrayList<>()));
        kanbanBoardEntity.addSection(new KanbanSectionEntity(null, "Terminé", "#2ecc71", 3L, new ArrayList<>()));
        return saveKanbanBoard(kanbanBoardEntity);
    }
}
