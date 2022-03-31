package fr.istic.sir.kanban.aarzel.kanbanapp.converter;

import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanCardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import fr.istic.sir.kanban.aarzel.model.KanbanCardDTO;
import fr.istic.sir.kanban.aarzel.model.KanbanSectionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ConvertUtils class that will be used to
 * convert DTOs to Entities
 * convert Entities to DTOs
 */
@Component
public class ConverterUtils {

    /**
     * The ModelMapper used for conversions.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts one KanbanBoardDTO to a KanbanBoardEntity
     *
     * @param kanbanBoardDTO the DTO to convert
     * @return the converted Entity
     */
    public KanbanBoardEntity convertOneKanbanBoardDTOToEntity(KanbanBoardDTO kanbanBoardDTO) {
        return modelMapper.map(kanbanBoardDTO, KanbanBoardEntity.class);
    }

    /**
     * Converts one KanbanBoardEntity to a KanbanBoardDTO
     *
     * @param kanbanBoardEntity the Entity to convert
     * @return the converted DTO
     */
    public KanbanBoardDTO convertOneKanbanBoardEntityToDTO(KanbanBoardEntity kanbanBoardEntity) {
        return modelMapper.map(kanbanBoardEntity, KanbanBoardDTO.class);
    }

    /**
     * Converts one KanbanSectionDTO to a KanbanSectionEntity
     *
     * @param kanbanSectionDTO the DTO to convert
     * @return the converted Entity
     */
    public KanbanSectionEntity convertOneKanbanSectionDTOToEntity(KanbanSectionDTO kanbanSectionDTO) {
        return modelMapper.map(kanbanSectionDTO, KanbanSectionEntity.class);
    }

    /**
     * Converts one KanbanSectionEntity to a KanbanSectionDTO
     *
     * @param kanbanSectionEntity the Entity to convert
     * @return the converted DTO
     */
    public KanbanSectionDTO convertOneKanbanSectionEntityToDTO(KanbanSectionEntity kanbanSectionEntity) {
        return modelMapper.map(kanbanSectionEntity, KanbanSectionDTO.class);
    }

    /**
     * Converts one KanbanCardDTO to a KanbanCardEntity
     *
     * @param kanbanCardDTO the DTO to convert
     * @return the converted Entity
     */
    public KanbanCardEntity convertOneKanbanCardDTOToEntity(KanbanCardDTO kanbanCardDTO) {
        return modelMapper.map(kanbanCardDTO, KanbanCardEntity.class);
    }

    /**
     * Converts one KanbanCardEntity to a KanbanCardDTO
     *
     * @param kanbanCardEntity the Entity to convert
     * @return the converted DTO
     */
    public KanbanCardDTO convertOneKanbanCardEntityToDTO(KanbanCardEntity kanbanCardEntity) {
        return modelMapper.map(kanbanCardEntity, KanbanCardDTO.class);
    }
}
