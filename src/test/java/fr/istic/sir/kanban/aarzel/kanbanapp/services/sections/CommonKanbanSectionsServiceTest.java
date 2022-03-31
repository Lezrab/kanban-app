package fr.istic.sir.kanban.aarzel.kanbanapp.services.sections;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanSectionEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.repository.IKanbanSectionRepository;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.TestUtils;
import fr.istic.sir.kanban.aarzel.model.KanbanSectionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommonKanbanSectionsServiceTest {
    @Mock
    private IKanbanSectionRepository kanbanSectionRepository;
    @Mock
    private ConverterUtils converter;
    @InjectMocks
    private CommonKanbanSectionsService commonKanbanSectionsService;

    @Test
    @DisplayName("Should call repository method.")
    void testGetOneKanbanSectionById() throws DatabaseFetchException {
        when(kanbanSectionRepository.findById(1L)).thenReturn(Optional.of(TestUtils.nominalKanbanSectionEntity(1L)));
        Optional<KanbanSectionEntity> result = commonKanbanSectionsService.getOneKanbanSectionById(1L);
        assertNotNull(result);
        result.ifPresent(kanbanSectionEntity -> assertEquals("SectionLabel", kanbanSectionEntity.getLabel()));
        verify(kanbanSectionRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testGetOneKanbanSectionById_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanSectionRepository)
                .findById(1L);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanSectionsService.getOneKanbanSectionById(1L));
    }


    @Test
    @DisplayName("Should return true or false, if the KanbanSection exists or not.")
    void testKanbanSectionFoundInDbById() throws DatabaseFetchException {
        when(kanbanSectionRepository.existsById(1L)).thenReturn(true);
        Boolean result = commonKanbanSectionsService.kanbanSectionFoundInDbById(1L);
        assertEquals(Boolean.TRUE, result);

        when(kanbanSectionRepository.existsById(1L)).thenReturn(false);
        Boolean result2 = commonKanbanSectionsService.kanbanSectionFoundInDbById(1L);
        assertEquals(Boolean.FALSE, result2);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testKanbanSectionFoundInDbById_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanSectionRepository)
                .existsById(1L);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanSectionsService.kanbanSectionFoundInDbById(1L));
    }

    @Test
    @DisplayName("Should return true or false, if the KanbanSection exists or not.")
    void testKanbanSectionFoundInDbByLabel() throws DatabaseFetchException {
        when(kanbanSectionRepository.existsByLabel("Label")).thenReturn(true);
        Boolean result = commonKanbanSectionsService.kanbanSectionFoundInDbByLabel("Label");
        assertEquals(Boolean.TRUE, result);

        when(kanbanSectionRepository.existsByLabel("Label")).thenReturn(false);
        Boolean result2 = commonKanbanSectionsService.kanbanSectionFoundInDbByLabel("Label");
        assertEquals(Boolean.FALSE, result2);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testKanbanSectionFoundInDbByLabel_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanSectionRepository)
                .existsByLabel("Label");
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanSectionsService.kanbanSectionFoundInDbByLabel("Label"));
    }

    @Test
    @DisplayName("Should return true or false, if the KanbanSection exists or not.")
    void testKanbanSectionFoundInDbByPosition() throws DatabaseFetchException {
        when(kanbanSectionRepository.existsByPosition(1L)).thenReturn(true);
        Boolean result = commonKanbanSectionsService.kanbanSectionFoundInDbByPosition(1L);
        assertEquals(Boolean.TRUE, result);

        when(kanbanSectionRepository.existsByPosition(1L)).thenReturn(false);
        Boolean result2 = commonKanbanSectionsService.kanbanSectionFoundInDbByPosition(1L);
        assertEquals(Boolean.FALSE, result2);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testKanbanSectionFoundInDbByPosition_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanSectionRepository)
                .existsByPosition(1L);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanSectionsService.kanbanSectionFoundInDbByPosition(1L));
    }

    @Test
    @DisplayName("Should save the KanbanSection.")
    void testSaveKanbanSection() throws DatabaseFetchException {
        KanbanSectionEntity entity = TestUtils.nominalKanbanSectionEntity(1L);
        when(kanbanSectionRepository.save(entity)).thenReturn(entity);
        KanbanSectionEntity result = commonKanbanSectionsService.saveKanbanSection(entity);
        assertEquals(entity, result);
        verify(kanbanSectionRepository).save(entity);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testSaveKanbanSection_DATABASE_FETCH_EXCEPTION() {
        KanbanSectionEntity entity = TestUtils.nominalKanbanSectionEntity(1L);
        doThrow(new RuntimeException("Error!")).when(kanbanSectionRepository)
                .save(TestUtils.nominalKanbanSectionEntity(1L));
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanSectionsService.saveKanbanSection(entity));
    }

    @Test
    @DisplayName("Should delete the KanbanSection.")
    void testDeleteById() throws DatabaseFetchException {
        commonKanbanSectionsService.deleteById(1L);
        verify(kanbanSectionRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testDeleteById_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanSectionRepository)
                .deleteById(1L);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanSectionsService.deleteById(1L));
    }

    @Test
    @DisplayName("Should call the repository method.")
    void testConvertToEntityAndSaveKanbanSection() throws DatabaseFetchException {
        KanbanSectionEntity entityToSave = TestUtils.nominalKanbanSectionEntity(1L);
        KanbanSectionDTO dtoToSave = new KanbanSectionDTO().label("label");
        when(converter.convertOneKanbanSectionDTOToEntity(dtoToSave)).thenReturn(entityToSave);
        when(kanbanSectionRepository.save(entityToSave)).thenReturn(entityToSave);
        KanbanSectionEntity result = commonKanbanSectionsService.convertToEntityAndSaveKanbanSection(dtoToSave);
        assertNotNull(result);
        verify(kanbanSectionRepository).save(any());
    }

    @Test
    @DisplayName("Should get the repository.")
    void testGetKanbanBoardRepository() {
        IKanbanSectionRepository i = commonKanbanSectionsService.getKanbanSectionRepository();
        assertNotNull(i);
    }

    @Test
    @DisplayName("Should get the converter.")
    void testGetConverter() {
        ConverterUtils c = commonKanbanSectionsService.getConverter();
        assertNotNull(c);
    }
}

