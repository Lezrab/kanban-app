package fr.istic.sir.kanban.aarzel.kanbanapp.configuration;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanCardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import fr.istic.sir.kanban.aarzel.model.KanbanCardDTO;
import fr.istic.sir.kanban.aarzel.model.KanbanSectionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * Kanban App Configuration
 * Mainly used for mapping issues, when we create want to use CRUD operation
 * on the interface of the application, we won't pass the id of the entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Validated
@ConfigurationProperties(prefix = "config")
public class KanbanConfiguration {

    /**
     * If needed, set a property in the configuration.
     */
    @NotEmpty
    private String property;

    /**
     * Mappings made when a conversion is made between entities and DTOs.
     * The entity's id is always skipped, as we won't pass it to the DTO and vice-versa.
     * @return the modelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<KanbanBoardDTO, KanbanBoardEntity>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<KanbanSectionDTO, KanbanSectionEntity>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<KanbanCardDTO, KanbanCardEntity>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        return modelMapper;
    }
}
