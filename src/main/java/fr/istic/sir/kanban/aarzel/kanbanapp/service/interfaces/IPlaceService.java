package fr.istic.sir.kanban.aarzel.kanbanapp.service.interfaces;

import fr.istic.sir.kanban.aarzel.kanbanapp.model.entities.Place;

import java.util.List;
import java.util.Optional;

public interface IPlaceService {

    /**
     * Lists all the existing Place in the database.
     *
     * @return all the Place
     */
    List<Place> getAllPlaces();

    /**
     * Returns one specific Place using by its id.
     *
     * @param id the id of the Place to retrieve
     * @return the retrieved Place
     */
    Optional<Place> getPlaceById(Long id);

    /**
     * Saves a new Place and returns it.
     *
     * @param place the new Place to save
     * @return the saved Place
     */
    Place saveNewPlace(Place place);

    /**
     * Deletes a Place by its id
     *
     * @param id the place to delete
     * @return the id of the deleted Place
     */
    Long deletePlaceById(Long id);

    /**
     * Deletes all Place
     *
     * @return the number of deleted place
     */
    Long deleteAllPlace();

    /**
     *
     * @param id
     * @param place
     * @return
     */
    Optional<Place> updatePlace(Long id, Place place);
}
