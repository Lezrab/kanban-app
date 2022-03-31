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

import java.util.Optional;

@Transactional
@Service
public class UpdateKanbanSectionsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Autowired
    private CommonKanbanSectionsService commonKanbanSectionsService;

    /**
     * Updates a KanbanSection by its id
     *
     * @param boardId          the id of the board
     * @param sectionId          the id of the section to update
     * @param kanbanSectionDTO the KanbanSection content passed from the client to the server to update
     * @param LOGGER           the logger used to inform that the called was launched
     * @return the updated KanbanSection
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanBoard does not exist
     */
    public KanbanSectionDTO updateOneKanbanSectionInKanbanBoard(Long boardId, Long sectionId, KanbanSectionDTO kanbanSectionDTO, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to PUT API for updating KanbanSection with id [{}] " +
                "in KanbanBoard with id [{}] launched.", sectionId, boardId);
        Optional<KanbanBoardEntity> entity = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (entity.isPresent()) {
            Optional<KanbanSectionEntity> sectionEntity = commonKanbanSectionsService.getOneKanbanSectionById(sectionId);
            if (sectionEntity.isPresent()) {
                KanbanSectionEntity kanbanSection = commonKanbanBoardsService.getConverter().convertOneKanbanSectionDTOToEntity(kanbanSectionDTO);
                kanbanSection.setId(sectionEntity.get().getId());
                kanbanSection.getKanbanCards().addAll(sectionEntity.get().getKanbanCards());
                KanbanSectionEntity kanbanSectionSaved = commonKanbanSectionsService.saveKanbanSection(kanbanSection);
                return commonKanbanBoardsService.getConverter().convertOneKanbanSectionEntityToDTO(kanbanSectionSaved);
            } else {
                throw new ResourceNotFoundException(CommonKanbanSectionsService.KANBAN_SECTION_NOT_FOUND_WITH_ID + sectionId);
            }
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
    }
}
