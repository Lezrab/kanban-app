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
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The KanbanCardEntity
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_KANBAN_CARD")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class KanbanCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KC_ID", nullable = false)
    @ApiModelProperty(position = 1)
    private Long id;

    @Column(name = "KC_LABEL", nullable = false)
    @ApiModelProperty(position = 2)
    private String label;

    @Column(name = "KC_DESCRIPTION")
    @ApiModelProperty(position = 3)
    private String description;

    @Column(name = "KC_CREATION_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(position = 4)
    private LocalDate creationDate;

    @Column(name = "KC_ESTIMATED_TIME")
    @ApiModelProperty(position = 5)
    private Integer estimatedTime;

    @Column(name = "KC_ASSOCIATED_URL")
    @ApiModelProperty(position = 6)
    private String associatedUrl;

    @Column(name = "KC_AFFECTED_USER")
    @ApiModelProperty(position = 7)
    private String affectedUser;

    @ElementCollection
    @CollectionTable(name = "T_ASSOCIATED_TAGS", joinColumns = @JoinColumn(name = "AT_CARD_ID"))
    @Column(name = "AT_LABEL")
    @ApiModelProperty(position = 8)
    @Fetch(FetchMode.SUBSELECT)
    private List<String> associatedTags = new ArrayList<>();
}