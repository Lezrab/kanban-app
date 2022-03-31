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
public class PostKanbanCardsService {

    @Autowired
    private CommonKanbanBoardsService commonKanbanBoardsService;

    @Autowired
    private CommonKanbanSectionsService commonKanbanSectionsService;

    @Autowired
    private CommonKanbanCardsService commonKanbanCardsService;

    /**
     * Creates a KanbanCard by its id
     *
     * @param boardId       the id of the board
     * @param sectionId     the id of the section
     * @param id            the id of card to create
     * @param kanbanCardDTO the KanbanCard content passed from the client to the server to create
     * @param LOGGER        the logger used to inform that the called was launched
     * @return the created KanbanCard
     * @throws DatabaseFetchException    if there is a database error
     * @throws ResourceNotFoundException if the KanbanSection/KanbanBoard does not exist
     */
    public KanbanCardDTO createOneKanbanCardByKanbanSection(Long boardId, Long sectionId, Long id, KanbanCardDTO kanbanCardDTO, Logger LOGGER) throws DatabaseFetchException, ResourceNotFoundException {
        LOGGER.info("[SERVICE] - Call to POST API for creating Kanban Card with id [{}] in KanbanSection with id [{}] " + "in KanbanBoard with id [{}] launched.", id, sectionId, boardId);
        Optional<KanbanBoardEntity> boardInDb = commonKanbanBoardsService.getOneKanbanBoardById(boardId);
        if (boardInDb.isPresent()) {
            Optional<KanbanSectionEntity> sectionInDb = commonKanbanSectionsService.getOneKanbanSectionById(sectionId);
            if (sectionInDb.isPresent()) {
                KanbanCardEntity kanbanCard = commonKanbanCardsService.convertToEntityAndSaveKanbanCard(kanbanCardDTO);
                sectionInDb.get().addCard(kanbanCard);
                return commonKanbanBoardsService.getConverter().convertOneKanbanCardEntityToDTO(kanbanCard);
            } else {
                throw new ResourceNotFoundException(CommonKanbanSectionsService.KANBAN_SECTION_NOT_FOUND_WITH_ID + sectionId);
            }
        } else {
            throw new ResourceNotFoundException(CommonKanbanBoardsService.KANBAN_BOARD_NOT_FOUND_WITH_ID + boardId);
        }
    }
}
