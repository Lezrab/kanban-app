package fr.istic.sir.kanban.aarzel.kanbanapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.istic.sir.kanban.aarzel.kanbanapp.model.entities.Place;
import fr.istic.sir.kanban.aarzel.kanbanapp.service.interfaces.IPlaceService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PlaceController.class)
class TUPlaceController {

    @MockBean
    private IPlaceService placeService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper modelMapper;

    /*@Test
    void GetPlaces_shouldBeOkAndEmpty() throws Exception {
        mockMvc.perform(get("/api/places")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    void GetPlaces_shouldReturnPlace() throws Exception {
        Place place = new Place(
                1L,
                "00",
                "Street name",
                "City",
                "ZipCode",
                "Region",
                "Country"
        );

        List<Place> places = new ArrayList<>();
        places.add(place);

        when(placeService.getAllPlaces()).thenReturn(places);

        mockMvc.perform(get("/api/places"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$.[0].streetName", is("Street name")));
    }

    @Test
    void GetPlaces_shouldReturnPlaces() throws Exception {
        Place place = new Place(
                1L,
                "00",
                "Street name",
                "City",
                "ZipCode",
                "Region",
                "Country"
        );

        Place place2 = new Place(
                2L,
                "00",
                "Street name 2",
                "City 2",
                "ZipCode 2",
                "Region 2",
                "Country 2"
        );

        List<Place> places = new ArrayList<>();
        places.add(place);
        places.add(place2);

        when(placeService.getAllPlaces()).thenReturn(places);

        mockMvc.perform(get("/api/places"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.[0].streetName", is("Street name")))
                .andExpect(jsonPath("$.[1].streetName", is("Street name 2")));
    }

    @Test
    void GetPlace_shouldReturnPlace() throws Exception {
        Place place = new Place(
                1L,
                "00",
                "Street name",
                "City",
                "ZipCode",
                "Region",
                "France"
        );

        Optional<Place> placeOptional = Optional.of(place);

        when(placeService.getPlaceById(1L)).thenReturn(placeOptional);

        mockMvc.perform(get("/api/places/{placeId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", is("France")));
    }

    @Test
    void GetPlace_shouldReturn404() throws Exception {

        mockMvc.perform(get("/api/places/{placeId}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Couldn't retrieve card with id : 1")))
                .andExpect(jsonPath("$.methodInvoked", is("GET")))
                .andExpect(jsonPath("$.urlInvoked", is("http://localhost/api/places/1")));
    }

    @Test
    void PostPlace_shouldCreatePlace() throws Exception {
        Place place = new Place(
                1L,
                "00",
                "Street name",
                "City",
                "ZipCode",
                "Region",
                "France"
        );

        this.mockMvc.perform(post("/api/places/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(place)))
                .andExpect(status().isCreated());
    }

    @Test
    void PostPlace_shouldThrowValidationError() throws Exception {
        Place place = new Place(
                1L,
                "00",
                "Street name",
                "",
                "ZipCode",
                "Region",
                "France"
        );

        this.mockMvc.perform(post("/api/places/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(place)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.paramErrors[0].city", containsString("must")))
                .andExpect(jsonPath("$.paramErrors[1].city", containsString("must")));

        Place place2 = new Place(
                1L,
                "00",
                "StreetName",
                "ThisCityNameIsWayTooLongToBeValidatedByHibernateIsntIt?WellLetsCheckIt!",
                "ZipCode",
                "Region",
                "France"
        );

        this.mockMvc.perform(post("/api/places/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(place2)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.paramErrors.length()", is(1)))
                .andExpect(jsonPath("$.paramErrors[0].city", is("The size must be between 1 and 60.")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme