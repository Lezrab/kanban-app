package fr.istic.sir.kanban.aarzel.kanbanapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_KANBAN_BOARD")
public class KanbanBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kanban_board_entity_seq")
    @SequenceGenerator(name = "kanban_board_entity_seq")
    @Column(name = "KB_ID", nullable = false)
    private Long id;

    @Column(name = "KB_EXTERNAL_ID", nullable = false)
    private String externalId;
}