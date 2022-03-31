package fr.istic.sir.kanban.aarzel.kanbanapp.services.boards;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service used for : updating one KanbanBoard
 */
@Service
@Transactional
public class UpdateKanbanBoardsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    /**
     * Updates a KanbanBoard by its id
     *
     * @param id             the id of the KanbanBoard to update
     * @param kanbanBoardDTO the content of the updated KanbanBoard
     * @param LOGGER         the logger used to inform that the called was launched
     * @return the updated KanbanBoard
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanBoard does not exist
     */
    public KanbanBoardDTO putOneKanbanBoardByExternalId(Long id, KanbanBoardDTO kanbanBoardDTO, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to PUT API for updating Kanban Board with id [{}] launched.", id);
        Optional<KanbanBoardEntity> kanbanBoardEntityInDb = commonKanbanBoardsService.getOneKanbanBoardById(id);
        if (kanbanBoardEntityInDb.isPresent()) {
            ConverterUtils converter = commonKanbanBoardsService.getConverter();
            KanbanBoardEntity kanbanBoardEntity = converter.convertOneKanbanBoardDTOToEntity(kanbanBoardDTO);
            kanbanBoardEntity.setId(kanbanBoardEntityInDb.get().getId());
            kanbanBoardEntity.getKanbanSections().addAll(kanbanBoardEntityInDb.get().getKanbanSections());
            KanbanBoardEntity kanbanBoardEntitySaved = commonKanbanBoardsService.saveKanbanBoard(kanbanBoardEntity);
            return converter.convertOneKanbanBoardEntityToDTO(kanbanBoardEntitySaved);
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + id);
        }
    }
}
