package fr.istic.sir.kanban.aarzel.kanbanapp.repository;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for KanbanSection
 */
public interface IKanbanSectionRepository extends JpaRepository<KanbanSectionEntity, Long> {

    Boolean existsByLabel(String label);

    Boolean existsByPosition(Long position);

    void deleteAll();
}
