package fr.istic.sir.kanban.aarzel.kanbanapp.repository;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for KanbanBoard
 */
public interface IKanbanBoardRepository extends JpaRepository<KanbanBoardEntity, Long> {

    Optional<KanbanBoardEntity> findByLabel(String label);

    Boolean existsByLabel(String label);
}
