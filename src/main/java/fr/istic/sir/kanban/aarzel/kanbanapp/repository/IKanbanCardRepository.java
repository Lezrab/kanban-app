package fr.istic.sir.kanban.aarzel.kanbanapp.repository;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for KanbanCard
 */
public interface IKanbanCardRepository extends JpaRepository<KanbanCardEntity, Long> {

    Optional<KanbanCardEntity> findById(Long id);

}
