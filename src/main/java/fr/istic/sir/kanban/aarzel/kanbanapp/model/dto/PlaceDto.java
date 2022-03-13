package fr.istic.sir.kanban.aarzel.kanbanapp.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PlaceDto {
    private final Long id;
    @Size(min = 1, max = 10, message = "The size must be between 1 and 10.")
    @NotBlank(message = "The street number must not be empty.")
    private final String streetNumber;
    @Size(min = 1, max = 100, message = "The size must be between 1 and 100.")
    @NotBlank(message = "The street name must not be empty.")
    private final String streetName;
    @Size(min = 1, max = 60, message = "The size must be between 1 and 60.")
    @NotBlank(message = "The city name must not be empty.")
    private final String city;
    @Size(min = 1, max = 20, message = "The size must be between 1 and 20.")
    @NotBlank(message = "The city zip code must not be empty.")
    private final String zipCode;
    @Size(min = 1, max = 50, message = "The size must be between 1 and 50.")
    @NotBlank(message = "The region must not be empty.")
    private final String region;
    @Size(min = 1, max = 50, message = "The size must be between 1 and 50.")
    @NotBlank(message = "The country must not be empty.")
    private final String country;
}
