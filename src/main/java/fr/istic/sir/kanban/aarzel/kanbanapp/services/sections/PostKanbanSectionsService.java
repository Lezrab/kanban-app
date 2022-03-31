package fr.istic.sir.kanban.aarzel.kanbanapp.services.sections;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceAlreadyExistsException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.model.KanbanSectionDTO;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class PostKanbanSectionsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Autowired
    private CommonKanbanSectionsService commonKanbanSectionsService;

    /**
     * Creates a KanbanSection by its id
     *
     * @param boardId          the id of the board
     * @param kanbanSectionDTO the KanbanSection content passed from the client to the server to create
     * @param LOGGER           the logger used to inform that the called was launched
     * @return the created KanbanSection
     * @throws DatabaseFetchException         if there is a database error
     * @throws ResourceAlreadyExistsException if the KanbanBoard already exists
     * @throws ResourceNotFoundException      if the KanbanBoard does not exist
     */
    public KanbanSectionDTO createOneKanbanSectionInKanbanBoard(Long boardId, KanbanSectionDTO kanbanSectionDTO, Logger LOGGER) throws DatabaseFetchException, ResourceAlreadyExistsException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to POST API for creating KanbanSection with label [{}] " +
                "in KanbanBoard with id [{}] launched.", kanbanSectionDTO.getLabel(), boardId);
        Optional<KanbanBoardEntity> entity = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (entity.isPresent()) {
            if (Boolean.FALSE.equals((commonKanbanSectionsService.kanbanSectionFoundInDbByLabel(kanbanSectionDTO.getLabel())))
                    && Boolean.FALSE.equals(commonKanbanSectionsService.kanbanSectionFoundInDbByPosition(kanbanSectionDTO.getPosition()))) {
                KanbanSectionEntity kanbanSection = commonKanbanSectionsService.convertToEntityAndSaveKanbanSection(kanbanSectionDTO);
                entity.get().addSection(kanbanSection);
                return commonKanbanBoardsService.getConverter().convertOneKanbanSectionEntityToDTO(kanbanSection);
            } else {
                throw new ResourceAlreadyExistsException(CommonKanbanSectionsService.KANBAN_SECTION_ALREADY_EXISTS + kanbanSectionDTO.getLabel());
            }
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
    }
}
