package fr.istic.sir.kanban.aarzel.kanbanapp.services.sections;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.model.KanbanSectionDTO;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class GetKanbanSectionsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Autowired
    private CommonKanbanSectionsService commonKanbanSectionsService;

    /**
     * Gets a KanbanSection by its id
     *
     * @param boardId   the id of the board
     * @param sectionId the id of section to get
     * @param LOGGER    the logger used to inform that the called was launched
     * @return the retrieved KanbanSection
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanBoard does not exist
     */
    public KanbanSectionDTO getKanbanSectionByKanbanBoard(Long boardId, Long sectionId, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to GET API for retrieving KanbanSection with id [{}] " +
                "in KanbanBoard with id [{}] launched.", sectionId, boardId);
        Optional<KanbanBoardEntity> boardInDb = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (boardInDb.isPresent()) {
            Optional<KanbanSectionEntity> kanbanSectionEntity = commonKanbanSectionsService.getOneKanbanSectionById(sectionId);
            if (kanbanSectionEntity.isPresent()) {
                return commonKanbanBoardsService.getConverter().convertOneKanbanSectionEntityToDTO(kanbanSectionEntity.get());
            } else {
                throw new ResourceNotFoundException(CommonKanbanSectionsService.KANBAN_SECTION_NOT_FOUND_WITH_ID + sectionId);
            }
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
    }

    /**
     * Gets many/all KanbanSection
     *
     * @param boardId the id of the board
     * @param LOGGER  the logger used to inform that the called was launched
     * @return the retrieved KanbanSections
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanBoard does not exist
     */
    public List<KanbanSectionDTO> getKanbanSectionsByKanbanBoard(Long boardId, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        List<KanbanSectionDTO> kanbanSectionDTOList;
        LOGGER.info("[SERVICE] - Call to GET API for retrieving all KanbanSections in KanbanBoard with id [{}] launched.", boardId);
        Optional<KanbanBoardEntity> boardInDb = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (boardInDb.isPresent()) {
            kanbanSectionDTOList = boardInDb.get().getKanbanSections().stream()
                    .map(entity -> commonKanbanBoardsService.getConverter().convertOneKanbanSectionEntityToDTO(entity)).collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
        return kanbanSectionDTOList;
    }
}
