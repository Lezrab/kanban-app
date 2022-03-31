package fr.istic.sir.kanban.aarzel.kanbanapp.services.boards;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service used for : getting one KanbanBoard, many/all KanbanBoards
 */
@Transactional
@Service
public class GetKanbanBoardsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    /**
     * Gets a KanbanBoard by its id
     *
     * @param id     the id of the KanbanBoard to get
     * @param LOGGER the logger used to inform that the called was launched
     * @return the returned KanbanBoard
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanBoard does not exist
     */
    public KanbanBoardDTO getOneKanbanBoardById(Long id, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to GET API for retrieving Kanban Board with id [{}] launched.", id);
        Optional<KanbanBoardEntity> boardInDb = commonKanbanBoardsService.getOneKanbanBoardById(id);
        if (boardInDb.isPresent()) {
            return commonKanbanBoardsService.getConverter().convertOneKanbanBoardEntityToDTO(boardInDb.get());
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + id);
        }
    }

    /**
     * Gets many/all KanbanBoard
     *
     * @param LOGGER the logger used to inform that the called was launched
     * @return the returned KanbanBoards
     * @throws DatabaseFetchException if there is a database error
     */
    public List<KanbanBoardDTO> getManyKanbanBoards(Logger LOGGER) throws DatabaseFetchException {
        LOGGER.info("[SERVICE] - Call to GET API for retrieving Kanban Boards launched.");
        List<KanbanBoardEntity> kanbanBoardEntities = commonKanbanBoardsService.getAllKanbanBoardEntities();
        return kanbanBoardEntities.stream().map(entity -> commonKanbanBoardsService.getConverter().convertOneKanbanBoardEntityToDTO(entity)).collect(Collectors.toList());
    }
}
