package fr.istic.sir.kanban.aarzel.kanbanapp.services;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanCardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import fr.istic.sir.kanban.aarzel.model.KanbanCardDTO;
import fr.istic.sir.kanban.aarzel.model.KanbanSectionDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static KanbanBoardEntity nominalKanbanBoardEntity(Long id) {
        return new KanbanBoardEntity(id, "BoardLabel", new ArrayList<>());
    }

    public static KanbanBoardDTO nominalKanbanBoardDto() {
        return new KanbanBoardDTO().label("myKanbanBoard");
    }

    public static KanbanSectionEntity nominalKanbanSectionEntity(Long id) {
        return new KanbanSectionEntity(id, "SectionLabel", "#000000", 1L, new ArrayList<>());
    }

    public static KanbanSectionDTO nominalKanbanSectionDto() {
        return new KanbanSectionDTO().label("myKanbanSection").hexColor("#000000").position(1L);
    }

    public static KanbanCardEntity nominalKanbanCardEntity(Long id) {
        return new KanbanCardEntity(id, "CardLabel", "CardDescription", LocalDate.now(), 60, "CardUrl", "CardUser", new ArrayList<>());
    }

    public static KanbanCardDTO nominalKanbanCardDto() {
        return new KanbanCardDTO().label("myKanbanCard").description("card description")
                .creationDate(LocalDate.now()).estimatedTime(60)
                .associatedUrl("http://www.xyz.com/").affectedUser("john_doe").associatedTags(List.of("TODO"));
    }
}
