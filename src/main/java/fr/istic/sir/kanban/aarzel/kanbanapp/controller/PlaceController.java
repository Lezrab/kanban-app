package fr.istic.sir.kanban.aarzel.kanbanapp.controller;

import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.model.dto.PlaceDto;
import fr.istic.sir.kanban.aarzel.kanbanapp.model.dto.PostDto;
import fr.istic.sir.kanban.aarzel.kanbanapp.model.entities.Place;
import fr.istic.sir.kanban.aarzel.kanbanapp.response.GenericDeleteResponse;
import fr.istic.sir.kanban.aarzel.kanbanapp.service.interfaces.IPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "KanbanController/PlaceController", description = "Place Controller")
public class PlaceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceController.class);

    private IPlaceService placeService;

    private ModelMapper modelMapper;

    @Autowired
    public PlaceController(IPlaceService placeService, ModelMapper modelMapper) {
        this.placeService = placeService;
        this.modelMapper = modelMapper;
    }

    /**
     * API route for: Getting all Place object.
     * This route returns every listed Place object in the database as a List of Place.
     * @return the List of Place
     */
    @GetMapping(value = "/places")
    @ResponseBody
    @Operation(summary = "Get the list of all created Kanban places.", tags = "getPlaces")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    public ResponseEntity<List<PlaceDto>> getPlaces() {
        LOGGER.info("[INFO] - getPlaces API GET call launched !");
        List<PlaceDto> placesList = placeService.getAllPlaces()
                .stream()
                .map(place -> modelMapper.map(place, PlaceDto.class))
                .collect(toList());
        return ResponseEntity.ok().body(placesList);
    }


    /**
     * API route for: Getting one Place object by id.
     * This route returns the listed Place object with the given id in the database as a Place object.
     * @param placeId the Place to return
     * @return the Place
     * @throws ResourceNotFoundException if the place does not exist.
     */
    @GetMapping("/places/{placeId}")
    @ResponseBody
    @Operation(summary = "Get one specific Kanban place.", tags = "getPlace")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    public ResponseEntity<PlaceDto> getPlace(@PathVariable final Long placeId) {
        LOGGER.info("[INFO] - getPlace API GET call launched !");
        Optional<Place> place = placeService.getPlaceById(placeId);
        PlaceDto getResponse = modelMapper.map(place, PlaceDto.class);
        return ResponseEntity.ok().body(getResponse);
    }

    /**
     * API route for: Creating one Place by giving a Place body as JSON.
     * This route returns the created Place object.
     * @param placeDto the Place to create
     * @return the created Place object
     */
    @PostMapping(value = "/places",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a new Kanban place.", tags = "postPlace")
    @ApiResponse(responseCode = "201", description = "OK, the Place was correctly created.")
    public ResponseEntity<Place> postPlace(@RequestBody @Valid PlaceDto placeDto) {
        LOGGER.info("[INFO] - postPlace API POST call launched !");
        Place postRequest = modelMapper.map(placeDto, Place.class);
        Place post = placeService.saveNewPlace(postRequest);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    /**
     * API route for: Deleting a Place object by its id.
     * This route returns the id of the deleted Place object.
     * @param placeId the Place to delete
     * @return the id of the deleted Place
     * @throws ResourceNotFoundException if the Place does not exist.
     */
    @DeleteMapping("/places/{placeId}")
    @Operation(summary = "Delete the corresponding Place.", tags = "deletePlace")
    @ApiResponse(responseCode = "200", description = "OK, the Place was correctly deleted.")
    @ApiResponse(responseCode = "404", description = "NOT FOUND, the Place couldn't be found.")
    public ResponseEntity<GenericDeleteResponse> deletePlace(@PathVariable final Long placeId) {
        placeService.deletePlaceById(placeId);
        GenericDeleteResponse response = new GenericDeleteResponse("The place was deleted", String.format("Card with id: %d was deleted", placeId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * API route for: Deleting every Place object of the database.
     * This route returns the number of Place deleted.
     * @return the number of place deleted
     */
    @DeleteMapping("/places")
    @Operation(summary = "Delete all places.", tags = "deletePlaces")
    @ApiResponse(responseCode = "200", description = "OK, the Places were correctly deleted.")
    public ResponseEntity<GenericDeleteResponse> deletePlaces() {
        Long nbDeletions = placeService.deleteAllPlace();
        GenericDeleteResponse response = new GenericDeleteResponse("All the places were deleted", String.format("Number of places deleted: %d", nbDeletions));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * API route for: Updating a Place by its id.
     * This route returns the body of the updated Place.
     * @param placeId the id of the Place to update.
     * @param placeDto the new Place to save.
     * @return the updated Place.
     * @throws ResourceNotFoundException if the Place does not exist.
     */
    @PutMapping("/places/{placeId}")
    public ResponseEntity<PlaceDto> updatePlaceById(@PathVariable long placeId, @RequestBody PlaceDto placeDto) {
        /*Optional<Place> placeOpt = placeService.getPlaceById(placeId);
        if (placeOpt.isEmpty()) {
            throw new ResourceNotFoundException("Couldn't retrieve card with id : " + placeId);
        }

        Place placeResponse = placeOpt.get();*/
        return null;
        //return new ResponseEntity<>(placeResponse, HttpStatus.CREATED);
    }


}
