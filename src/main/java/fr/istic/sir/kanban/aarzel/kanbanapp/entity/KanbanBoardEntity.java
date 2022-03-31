package fr.istic.sir.kanban.aarzel.kanbanapp.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The KanbanBoardEntity
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_KANBAN_BOARD")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = KanbanBoardEntity.class)
public class KanbanBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KB_ID", nullable = false)
    @ApiModelProperty(position = 1)
    private Long id;

    @Column(name = "KB_LABEL", nullable = false)
    @ApiModelProperty(position = 2)
    private String label;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "KS_BOARD_ID")
    @ApiModelProperty(position = 3)
    @Fetch(FetchMode.SUBSELECT)
    private List<KanbanSectionEntity> kanbanSections = new ArrayList<>();

    public void addSection(KanbanSectionEntity kanbanSection) {
        if (Objects.isNull(kanbanSections)) {
            kanbanSections = new ArrayList<>();
        }
        kanbanSections.add(kanbanSection);
    }

    public void removeSection(KanbanSectionEntity kanbanSection) {
        if (Objects.isNull(kanbanSections)) {
            kanbanSections = new ArrayList<>();
        }
        kanbanSections.remove(kanbanSection);
    }

    public void removeAll() {
        if (Objects.isNull(kanbanSections)) {
            kanbanSections = new ArrayList<>();
        }
        kanbanSections.clear();
    }
}