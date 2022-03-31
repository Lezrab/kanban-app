package fr.istic.sir.kanban.aarzel.kanbanapp.services.cards;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanCardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.sections.CommonKanbanSectionsService;
import fr.istic.sir.kanban.aarzel.model.KanbanCardDTO;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UpdateKanbanCardsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Autowired
    private CommonKanbanSectionsService commonKanbanSectionsService;

    @Autowired
    private CommonKanbanCardsService commonKanbanCardsService;

    /***
     * Updates a KanbanCard by its id
     * @param boardId the id of the board
     * @param sectionId the id of the section
     * @param id the id of card to update
     * @param kanbanCardDTO the KanbanCard content passed from the client to the server to update
     * @param LOGGER the logger used to inform that the called was launched
     * @return the updated KanbanCard
     * @throws DatabaseFetchException if there is a database error
     * @throws ResourceNotFoundException if the KanbanCard/KanbanSection/KanbanBoard does not exist
     */
    public KanbanCardDTO updateOneKanbanCardByKanbanSection(Long boardId, Long sectionId, Long id, KanbanCardDTO kanbanCardDTO, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to PUT API for updating Kanban Card with id [{}] in KanbanSection with id [{}] " + "in KanbanBoard with id [{}] launched.", id, sectionId, boardId);
        Optional<KanbanBoardEntity> boardInDb = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (boardInDb.isPresent()) {
            Optional<KanbanSectionEntity> sectionInDb = commonKanbanSectionsService.getOneKanbanSectionById(sectionId);
            if (sectionInDb.isPresent()) {
                Optional<KanbanCardEntity> kanbanCard = commonKanbanCardsService.getOneKanbanCardById(id);
                if (kanbanCard.isPresent()) {
                    KanbanCardEntity kanbanCardEntity = commonKanbanBoardsService.getConverter().convertOneKanbanCardDTOToEntity(kanbanCardDTO);
                    kanbanCardEntity.setId(kanbanCard.get().getId());
                    KanbanCardEntity kanbanCardEntitySaved = commonKanbanCardsService.saveKanbanCard(kanbanCardEntity);
                    return commonKanbanBoardsService.getConverter().convertOneKanbanCardEntityToDTO(kanbanCardEntitySaved);
                } else {
                    throw new ResourceNotFoundException(CommonKanbanCardsService.KANBAN_CARD_NOT_FOUND_WITH_ID + sectionId);
                }
            } else {
                throw new ResourceNotFoundException(CommonKanbanSectionsService.KANBAN_SECTION_NOT_FOUND_WITH_ID + sectionId);
            }
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
    }
}
