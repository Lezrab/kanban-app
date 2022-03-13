package fr.istic.sir.kanban.aarzel.kanbanapp.service.impl;

import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.model.entities.Place;
import fr.istic.sir.kanban.aarzel.kanbanapp.repository.IPlaceRepository;
import fr.istic.sir.kanban.aarzel.kanbanapp.service.interfaces.IPlaceService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service which is used to proceed CRUD operation on the Place model.
 */
@Data
@Service
public class PlaceServiceImpl implements IPlaceService {

    // CrudRepository for using basic CRUD operations
    private final IPlaceRepository iPlaceRepository;
    //

    // Log4j logger for debugging
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceServiceImpl.class);
    //

    /**
     * Constructor of the PlaceServiceImpl class.
     *
     * @param iPlaceRepository the crud repository
     */
    @Autowired
    public PlaceServiceImpl(IPlaceRepository iPlaceRepository) {
        super();
        this.iPlaceRepository = iPlaceRepository;
    }

    /**
     * ${@inheritDoc}
     *
     * @return all the Place
     */
    @Override
    public List<Place> getAllPlaces() {
        return iPlaceRepository.findAll();
    }

    /**
     * ${@inheritDoc}
     *
     * @param id the id of the Place to retrieve
     * @return the retrieved Place
     * @throws ResourceNotFoundException if the place does not exist.
     */
    @Override
    public Optional<Place> getPlaceById(Long id) {
        return Optional.of(this.utilFindPlaceOrThrowError(id));
    }

    /**
     * ${@inheritDoc}
     *
     * @param place the new Place to save
     * @return the saved Place
     */
    @Override
    public Place saveNewPlace(Place place) {
        return iPlaceRepository.save(place);
    }

    /**
     * ${@inheritDoc}
     * @param id the place to delete
     * @return
     */
    @Override
    public Long deletePlaceById(Long id) {
        Place place = this.utilFindPlaceOrThrowError(id);
        iPlaceRepository.delete(place);
        return id;
    }

    /**
     * ${@inheritDoc}
     * @return
     */
    @Override
    public Long deleteAllPlace() {
        Long nbPlaces = iPlaceRepository.count();
        iPlaceRepository.deleteAll();
        return nbPlaces;
    }

    /**
     *
     * @param id
     * @param place
     * @return
     */
    @Override
    public Optional<Place> updatePlace(Long id, Place place) {
        Place placeRequest = this.utilFindPlaceOrThrowError(id);

        if (place.getCity() != null) {
            placeRequest.setCity(place.getCity());
        }

        if (place.getCountry() != null) {
            placeRequest.setCity(place.getCountry());
        }

        if (place.getRegion() != null) {
            placeRequest.setCity(place.getRegion());
        }

        if (place.getStreetName() != null) {
            placeRequest.setCity(place.getStreetName());
        }

        if (place.getStreetNumber() != null) {
            placeRequest.setCity(place.getStreetNumber());
        }

        if (place.getZipCode() != null) {
            placeRequest.setCity(place.getZipCode());
        }

        return Optional.of(iPlaceRepository.save(placeRequest));
    }

    /**
     *
     *
     * @param id
     * @return
     */
    private Place utilFindPlaceOrThrowError(Long id) {
        return iPlaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't retrieve card with id : " + id));
    }


}
