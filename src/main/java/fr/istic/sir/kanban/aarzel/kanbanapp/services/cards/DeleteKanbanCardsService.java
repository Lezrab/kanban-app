package fr.istic.sir.kanban.aarzel.kanbanapp.services.cards;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanCardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.CommonKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.sections.CommonKanbanSectionsService;
import org.apache.commons.collections4.ListUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service used for : deleting one KanbanCard, many/all KanbanCards
 */
@Transactional
@Service
public class DeleteKanbanCardsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Autowired
    private CommonKanbanSectionsService commonKanbanSectionsService;

    @Autowired
    private CommonKanbanCardsService commonKanbanCardsService;

    /**
     * Deletes a KanbanCard by its id
     *
     * @param boardId   the id of the board
     * @param sectionId the id of the section
     * @param cardId    the id of card to delete
     * @param LOGGER    the logger used to inform that the called was launched
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanCard/KanbanSection/KanbanBoard does not exist
     */
    public void deleteOneKanbanCardByKanbanSection(Long boardId, Long sectionId, Long cardId, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to DELETE API for deleting Kanban Card with id [{}] in KanbanSection with id [{}] " +
                "in KanbanBoard with id [{}] launched.", cardId, sectionId, boardId);
        Optional<KanbanBoardEntity> kanbanBoardEntity = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (kanbanBoardEntity.isPresent()) {
            Optional<KanbanSectionEntity> kanbanSectionEntity = commonKanbanSectionsService.getOneKanbanSectionById(sectionId);
            if (kanbanSectionEntity.isPresent()) {
                Optional<KanbanCardEntity> kanbanCardEntity = commonKanbanCardsService.getOneKanbanCardById(cardId);
                if (kanbanCardEntity.isPresent()) {
                    commonKanbanCardsService.deleteById(cardId);
                    kanbanSectionEntity.get().getKanbanCards().remove(kanbanCardEntity.get());
                } else {
                    throw new ResourceNotFoundException(CommonKanbanCardsService.KANBAN_CARD_NOT_FOUND_WITH_ID + cardId);
                }
            } else {
                throw new ResourceNotFoundException(CommonKanbanSectionsService.KANBAN_SECTION_NOT_FOUND_WITH_ID + sectionId);
            }
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
    }

    /**
     * Deletes many/all KanbanCard
     *
     * @param boardId   the id of the board
     * @param sectionId the id of the section
     * @param LOGGER    the logger used to inform that the called was launched
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanBoard/KanbanSection does not exist
     */
    public void deleteKanbanCardsByKanbanSection(Long boardId, Long sectionId, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to DELETE API for deleting all Kanban Sections launched in KanbanBoard with id [{}] launched.", boardId);
        Optional<KanbanBoardEntity> kanbanBoardEntity = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (kanbanBoardEntity.isPresent()) {
            Optional<KanbanSectionEntity> kanbanSectionEntity = commonKanbanSectionsService.getOneKanbanSectionById(sectionId);
            if (kanbanSectionEntity.isPresent()) {
                List<KanbanCardEntity> toBeRemoved = new ArrayList<>();
                for (KanbanCardEntity card : ListUtils.emptyIfNull(kanbanSectionEntity.get().getKanbanCards())) {
                    commonKanbanCardsService.deleteById(card.getId());
                    toBeRemoved.add(card);
                }
                kanbanSectionEntity.get().getKanbanCards().removeAll(toBeRemoved);
            } else {
                throw new ResourceNotFoundException(CommonKanbanSectionsService.KANBAN_SECTION_NOT_FOUND_WITH_ID + sectionId);
            }
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
    }
}
