package fr.istic.sir.kanban.aarzel.kanbanapp.services.sections;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.repository.IKanbanSectionRepository;
import fr.istic.sir.kanban.aarzel.model.KanbanSectionDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService.DATABASE_ERROR_MESSAGE;

/**
 * Common methods for KanbanSection
 */
@Service
@Transactional
@Getter
public class CommonKanbanSectionsService {

    @Autowired
    private IKanbanSectionRepository kanbanSectionRepository;

    @Autowired
    private ConverterUtils converter;

    public static final String KANBAN_SECTION_NOT_FOUND_WITH_ID = "Kanban section not found with id : ";
    public static final String KANBAN_SECTION_ALREADY_EXISTS = "Kanban section already exists in database with id : ";

    /**
     * Returns one KanbanSection by its id
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param id the id of the section
     * @return the section
     * @throws DatabaseFetchException if there is a database error
     */
    public Optional<KanbanSectionEntity> getOneKanbanSectionById(Long id) throws DatabaseFetchException {
        Optional<KanbanSectionEntity> kanbanBoardEntity;
        try {
            kanbanBoardEntity = kanbanSectionRepository.findById(id);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanBoardEntity;
    }

    /**
     * Checks if a KanbanSection exist in db by id
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param id the id of the section
     * @return true/false
     * @throws DatabaseFetchException if there is a database error
     */
    public Boolean kanbanSectionFoundInDbById(Long id) throws DatabaseFetchException {
        Boolean kanbanSectionExists;
        try {
            kanbanSectionExists = kanbanSectionRepository.existsById(id);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanSectionExists;
    }

    /**
     * Checks if a KanbanSection exist in db by label
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param label the label of the section
     * @return true/false
     * @throws DatabaseFetchException if there is a database error
     */
    public Boolean kanbanSectionFoundInDbByLabel(String label) throws DatabaseFetchException {
        Boolean kanbanSectionExists;
        try {
            kanbanSectionExists = kanbanSectionRepository.existsByLabel(label);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanSectionExists;
    }

    /**
     * Checks if a KanbanSection exist in db by position
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param position the position of the section
     * @return true/false
     * @throws DatabaseFetchException if there is a database error
     */
    public Boolean kanbanSectionFoundInDbByPosition(Long position) throws DatabaseFetchException {
        Boolean kanbanSectionExists;
        try {
            kanbanSectionExists = kanbanSectionRepository.existsByPosition(position);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanSectionExists;
    }

    /**
     * Saves a KanbanSection in db
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param kanbanSectionToSave the KanbanCard to save
     * @return the saved KanbanCard
     * @throws DatabaseFetchException if there is a database error
     */
    public KanbanSectionEntity saveKanbanSection(KanbanSectionEntity kanbanSectionToSave) throws DatabaseFetchException {
        KanbanSectionEntity kanbanSectionEntity;
        try {
            kanbanSectionEntity = kanbanSectionRepository.save(kanbanSectionToSave);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
        return kanbanSectionEntity;
    }

    /**
     * Deletes a KanbanSection by its id
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param id the id of the section
     * @throws DatabaseFetchException if there is a database error
     */
    public void deleteById(Long id) throws DatabaseFetchException {
        try {
            kanbanSectionRepository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseFetchException(DATABASE_ERROR_MESSAGE, e);
        }
    }

    /**
     * Converts a kanbanSectionDTO to an entity, and save it.
     * This method calls the repository, and throw an exception
     * if an error happens.
     *
     * @param kanbanSectionDTO the DTO passed from the client to the server
     * @return the created KanbanSection in db
     * @throws DatabaseFetchException if there is a database error
     */
    public KanbanSectionEntity convertToEntityAndSaveKanbanSection(KanbanSectionDTO kanbanSectionDTO) throws DatabaseFetchException {
        KanbanSectionEntity kanbanSection = converter.convertOneKanbanSectionDTOToEntity(kanbanSectionDTO);
        return saveKanbanSection(kanbanSection);
    }
}
