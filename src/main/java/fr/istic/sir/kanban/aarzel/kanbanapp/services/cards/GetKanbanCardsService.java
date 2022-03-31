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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class GetKanbanCardsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Autowired
    private CommonKanbanSectionsService commonKanbanSectionsService;

    @Autowired
    private CommonKanbanCardsService commonKanbanCardsService;

    /**
     * Gets a KanbanCard by its id
     *
     * @param boardId   the id of the board
     * @param sectionId the id of the section
     * @param cardId    the id of card to get
     * @param LOGGER    the logger used to inform that the called was launched
     * @return the retrieved KanbanCard
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanCard/KanbanSection/KanbanBoard does not exist
     */
    public KanbanCardDTO getOneKanbanCardByKanbanSection(Long boardId, Long sectionId, Long cardId, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to GET API for retrieving Kanban Card with id [{}] in KanbanSection with id [{}] " +
                "in KanbanBoard with id [{}] launched.", cardId, sectionId, boardId);
        Optional<KanbanBoardEntity> boardInDb = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (boardInDb.isPresent()) {
            Optional<KanbanSectionEntity> sectionInDb = commonKanbanSectionsService.getOneKanbanSectionById(sectionId);
            if (sectionInDb.isPresent()) {
                Optional<KanbanCardEntity> cardInDb = commonKanbanCardsService.getOneKanbanCardById(cardId);
                if (cardInDb.isPresent()) {
                    return commonKanbanBoardsService.getConverter().convertOneKanbanCardEntityToDTO(cardInDb.get());
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
     * Gets many/all KanbanCards
     *
     * @param boardId   the id of the board
     * @param sectionId the id of the section
     * @param LOGGER    the logger used to inform that the called was launched
     * @return the retrieved KanbanCards
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanSection/KanbanBoard does not exist
     */
    public List<KanbanCardDTO> getKanbanCardsByKanbanSection(Long boardId, Long sectionId, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        List<KanbanCardDTO> kanbanCardDTOList;
        LOGGER.info("[SERVICE] - Call to GET API for getting Kanban Cards in KanbanSection with id [{}] " +
                "in KanbanBoard with id [{}] launched.", sectionId, boardId);
        Optional<KanbanBoardEntity> boardInDb = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (boardInDb.isPresent()) {
            Optional<KanbanSectionEntity> sectionInDb = commonKanbanSectionsService.getOneKanbanSectionById(sectionId);
            if (sectionInDb.isPresent()) {
                kanbanCardDTOList = sectionInDb.get().getKanbanCards().stream()
                        .map(entity -> commonKanbanBoardsService.getConverter().convertOneKanbanCardEntityToDTO(entity)).collect(Collectors.toList());
            } else {
                throw new ResourceNotFoundException(CommonKanbanSectionsService.KANBAN_SECTION_NOT_FOUND_WITH_ID + sectionId);
            }
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
        return kanbanCardDTOList;
    }
}
