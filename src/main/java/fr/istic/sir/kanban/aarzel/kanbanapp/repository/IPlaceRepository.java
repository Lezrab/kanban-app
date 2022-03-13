package fr.istic.sir.kanban.aarzel.kanbanapp.repository;

import fr.istic.sir.kanban.aarzel.kanbanapp.model.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlaceRepository extends JpaRepository<Place, Long> {

}
