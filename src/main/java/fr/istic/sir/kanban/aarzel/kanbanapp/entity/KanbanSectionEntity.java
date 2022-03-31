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
 * The KanbanSectionEntity
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_KANBAN_SECTION")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = KanbanSectionEntity.class)
public class KanbanSectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KS_ID")
    @ApiModelProperty(position = 1)
    private Long id;

    @Column(name = "KS_LABEL", nullable = false)
    @ApiModelProperty(position = 2)
    private String label;

    @Column(name = "KS_COLOR", nullable = false)
    @ApiModelProperty(position = 3)
    private String hexColor;

    @Column(name = "KS_POSITION", nullable = false)
    @ApiModelProperty(position = 4)
    private Long position;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "KC_SECTION_ID")
    @ApiModelProperty(position = 5)
    @Fetch(FetchMode.SUBSELECT)
    private List<KanbanCardEntity> kanbanCards = new ArrayList<>();

    public void addCard(KanbanCardEntity kanbanCard) {
        if (Objects.isNull(kanbanCards)) {
            kanbanCards = new ArrayList<>();
        }
        kanbanCards.add(kanbanCard);
    }

    public void removeSection(KanbanCardEntity kanbanCard) {
        if (Objects.isNull(kanbanCards)) {
            kanbanCards = new ArrayList<>();
        }
        kanbanCards.remove(kanbanCard);
    }
}