package fr.istic.sir.kanban.aarzel.kanbanapp.model.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "PLACE")
public class Place {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Schema
    private Long id;

    @Column(name="streetNumber")
    @Size(min = 1, max = 10, message = "The size must be between 1 and 10.")
    @NotBlank(message = "The street number must not be empty.")
    @Schema
    private String streetNumber;

    @Column(name="streetName")
    @Size(min = 1, max = 100, message = "The size must be between 1 and 100.")
    @NotBlank(message = "The street name must not be empty.")
    @Schema
    private String streetName;

    @Column(name="city")
    @Size(min = 1, max = 60, message = "The size must be between 1 and 60.")
    @NotBlank(message = "The city name must not be empty.")
    @Schema
    private String city;

    @Column(name="zipCode")
    @Size(min = 1, max = 20, message = "The size must be between 1 and 20.")
    @NotBlank(message = "The city zip code must not be empty.")
    @Schema
    private String zipCode;

    @Column(name="region")
    @Size(min = 1, max = 50, message = "The size must be between 1 and 50.")
    @NotBlank(message = "The region must not be empty.")
    @Schema
    private String region;

    @Column(name="country")
    @Size(min = 1, max = 50, message = "The size must be between 1 and 50.")
    @NotBlank(message = "The country must not be empty.")
    @Schema
    private String country;
}
