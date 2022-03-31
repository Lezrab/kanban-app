package fr.istic.sir.kanban.aarzel.kanbanapp.services.sections;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService;
import org.apache.commons.collections4.ListUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service used for : deleting one KanbanSection, many/all KanbanSections
 */
@Transactional
@Service
public class DeleteKanbanSectionsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Autowired
    private CommonKanbanSectionsService commonKanbanSectionsService;

    /**
     * Deletes a KanbanSection by its id
     *
     * @param boardId the id of the board
     * @param id      the id of section to delete
     * @param LOGGER  the logger used to inform that the called was launched
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanSection/KanbanBoard does not exist
     */
    public void deleteOneKanbanSectionInKanbanBoard(Long boardId, Long id, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to DELETE API for deleting Kanban Section with id [{}] in KanbanBoard with id [{}] launched.", id, boardId);
        Optional<KanbanBoardEntity> kanbanBoardEntity = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (kanbanBoardEntity.isPresent()) {
            Optional<KanbanSectionEntity> kanbanSectionEntity = commonKanbanSectionsService.getOneKanbanSectionById(id);
            if (kanbanSectionEntity.isPresent()) {
                commonKanbanSectionsService.deleteById(id);
                kanbanBoardEntity.get().getKanbanSections().remove(kanbanSectionEntity.get());
            } else {
                throw new ResourceNotFoundException(CommonKanbanSectionsService.KANBAN_SECTION_NOT_FOUND_WITH_ID + id);
            }
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
    }

    /**
     * Deletes many/all KanbanSection
     *
     * @param boardId the id of the board
     * @param LOGGER  the logger used to inform that the called was launched
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanBoard does not exist
     */
    public void deleteKanbanSectionsByKanbanBoard(Long boardId, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to DELETE API for deleting all Kanban Sections launched in KanbanBoard with id [{}] launched.", boardId);
        Optional<KanbanBoardEntity> kanbanBoardEntity = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (kanbanBoardEntity.isPresent()) {
            List<KanbanSectionEntity> toBeRemoved = new ArrayList<>();
            for (KanbanSectionEntity section : ListUtils.emptyIfNull(kanbanBoardEntity.get().getKanbanSections())) {
                commonKanbanSectionsService.deleteById(section.getId());
                toBeRemoved.add(section);
            }
            kanbanBoardEntity.get().getKanbanSections().removeAll(toBeRemoved);
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
    }
}
