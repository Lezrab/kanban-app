package fr.istic.sir.kanban.aarzel.kanbanapp.services.boards;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service used for : creating one KanbanBoard
 */
@Service
@Transactional
public class PostKanbanBoardsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    /**
     * Creates a KanbanBoard by its id
     *
     * @param id             the id of the KanbanBoard to created
     * @param kanbanBoardDTO the content of the created KanbanBoard
     * @param LOGGER         the logger used to inform that the called was launched
     * @return the created KanbanBoard
     * @throws DatabaseFetchException if there is a database error
     */
    public KanbanBoardDTO createOneKanbanBoardById(Long id, KanbanBoardDTO kanbanBoardDTO, Logger LOGGER) throws DatabaseFetchException {
        LOGGER.info("[SERVICE] - Call to POST API for creating Kanban Board with id [{}] launched.", id);
        KanbanBoardEntity kanbanBoardEntitySaved = commonKanbanBoardsService.convertToEntityAndSaveKanbanBoard(kanbanBoardDTO);
        return commonKanbanBoardsService.getConverter().convertOneKanbanBoardEntityToDTO(kanbanBoardEntitySaved);
    }
}
