package fr.istic.sir.kanban.aarzel.kanbanapp.services.boards;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import org.apache.commons.collections4.ListUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service used for : deleting one KanbanBoard, many/all KanbanBoards
 */
@Service
@Transactional
public class DeleteKanbanBoardsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    /**
     * Deletes a KanbanBoard by its id
     *
     * @param id     the id of the KanbanBoard to delete
     * @param LOGGER the logger used to inform that the called was launched
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanBoard does not exist
     */
    public void deleteOneKanbanBoardByExternalId(Long id, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to DELETE API for deleting Kanban Board with id [{}] launched.", id);
        if (Boolean.TRUE.equals(commonKanbanBoardsService.kanbanBoardFoundInDbId(id))) {
            commonKanbanBoardsService.deleteById(id);
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + id);
        }
    }

    /**
     * Delete many/all KanbanBoards
     *
     * @param LOGGER the logger used to inform that the called was launched
     * @throws DatabaseFetchException if there is a database error
     */
    public void deleteAllKanbanBoards(Logger LOGGER) throws DatabaseFetchException {
        LOGGER.info("[SERVICE] - Call to DELETE API for deleting all Kanban Boards launched.");
        List<KanbanBoardEntity> kanbanBoardEntities = commonKanbanBoardsService.getAllKanbanBoardEntities();
        for (KanbanBoardEntity entity : ListUtils.emptyIfNull(kanbanBoardEntities)) {
            commonKanbanBoardsService.deleteById(entity.getId());
        }
    }
}
