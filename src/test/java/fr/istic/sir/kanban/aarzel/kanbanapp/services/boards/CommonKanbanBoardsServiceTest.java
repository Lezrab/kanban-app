package fr.istic.sir.kanban.aarzel.kanbanapp.services.boards;

import fr.istic.sir.kanban.aarzel.kanbanapp.converter.ConverterUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.entity.KanbanBoardEntity;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.repository.IKanbanBoardRepository;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommonKanbanBoardsServiceTest {
    @Mock
    private IKanbanBoardRepository kanbanBoardRepository;
    @Mock
    private ConverterUtils converter;
    @InjectMocks
    private CommonKanbanBoardsService commonKanbanBoardsService;

    private final KanbanBoardEntity entityToSave = new KanbanBoardEntity(1L, "label", new ArrayList<>());


    @Test
    @DisplayName("Should return the KanbanBoardEntity (by id).")
    void testGetOneKanbanBoardById() throws DatabaseFetchException {
        when(kanbanBoardRepository.findById(1L)).thenReturn(Optional.of(new KanbanBoardEntity(1L, "KanbanBoard", null)));
        Optional<KanbanBoardEntity> result = commonKanbanBoardsService.getOneKanbanBoardById(1L);
        assertNotNull(result);
        result.ifPresent(kanbanBoardEntity -> assertEquals("KanbanBoard", kanbanBoardEntity.getLabel()));
        verify(kanbanBoardRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testGetOneKanbanBoardById_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanBoardRepository)
                .findById(1L);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanBoardsService.getOneKanbanBoardById(1L));
    }

    @Test
    @DisplayName("Should return the KanbanBoardEntity (by label).")
    void testGetOneKanbanBoardByLabel() throws DatabaseFetchException {
        when(kanbanBoardRepository.findByLabel("KanbanBoard")).thenReturn(Optional.of(new KanbanBoardEntity(1L, "KanbanBoard", null)));
        Optional<KanbanBoardEntity> result = commonKanbanBoardsService.getOneKanbanBoardByLabel("KanbanBoard");
        assertNotNull(result);
        result.ifPresent(kanbanBoardEntity -> assertEquals("KanbanBoard", kanbanBoardEntity.getLabel()));
        verify(kanbanBoardRepository).findByLabel("KanbanBoard");
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testGetOneKanbanBoardByLabel_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanBoardRepository)
                .findByLabel("KanbanBoard");
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanBoardsService.getOneKanbanBoardByLabel("KanbanBoard"));
    }

    @Test
    @DisplayName("Should return true or false, if the KanbanBoard exists or not.")
    void testKanbanBoardFoundInDbId() throws DatabaseFetchException {
        when(kanbanBoardRepository.existsById(1L)).thenReturn(true);
        Boolean result = commonKanbanBoardsService.kanbanBoardFoundInDbId(1L);
        assertEquals(Boolean.TRUE, result);

        when(kanbanBoardRepository.existsById(2L)).thenReturn(false);
        Boolean result2 = commonKanbanBoardsService.kanbanBoardFoundInDbId(2L);
        assertEquals(Boolean.FALSE, result2);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testKanbanBoardFoundInDbId_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanBoardRepository)
                .existsById(1L);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanBoardsService.kanbanBoardFoundInDbId(1L));
    }

    @Test
    @DisplayName("Should return true or false, if the KanbanBoard exists or not.")
    void testKanbanBoardFoundInDbByLabel() throws DatabaseFetchException {
        when(kanbanBoardRepository.existsByLabel("label1")).thenReturn(true);
        Boolean result = commonKanbanBoardsService.kanbanBoardFoundInDbByLabel("label1");
        assertEquals(Boolean.TRUE, result);

        when(kanbanBoardRepository.existsByLabel("label2")).thenReturn(false);
        Boolean result2 = commonKanbanBoardsService.kanbanBoardFoundInDbByLabel("label2");
        assertEquals(Boolean.FALSE, result2);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testKanbanBoardFoundInDbByLabel_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanBoardRepository)
                .existsByLabel("label");
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanBoardsService.kanbanBoardFoundInDbByLabel("label"));
    }

    @Test
    @DisplayName("Should return all KanbanBoards.")
    void testGetAllKanbanBoardEntities() throws DatabaseFetchException {
        when(kanbanBoardRepository.findAll()).thenReturn(List.of(
                new KanbanBoardEntity(1L, "Board1", null),
                new KanbanBoardEntity(2L, "Board2", null),
                new KanbanBoardEntity(3L, "Board3", null)));
        List<KanbanBoardEntity> result = commonKanbanBoardsService.getAllKanbanBoardEntities();
        assertEquals(3, result.size());
        assertEquals("Board1", result.get(0).getLabel());
        assertEquals("Board2", result.get(1).getLabel());
        assertEquals("Board3", result.get(2).getLabel());
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testGetAllKanbanBoardEntities_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanBoardRepository)
                .findAll();
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanBoardsService.getAllKanbanBoardEntities());
    }

    @Test
    @DisplayName("Should delete the KanbanBoard.")
    void testDeleteById() throws DatabaseFetchException {
        commonKanbanBoardsService.deleteById(1L);
        verify(kanbanBoardRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testDeleteById_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanBoardRepository)
                .deleteById(1L);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanBoardsService.deleteById(1L));
    }

    @Test
    @DisplayName("Should save the KanbanBoard.")
    void testSaveKanbanBoard() throws DatabaseFetchException {
        when(kanbanBoardRepository.save(entityToSave)).thenReturn(entityToSave);
        KanbanBoardEntity result = commonKanbanBoardsService.saveKanbanBoard(entityToSave);
        assertEquals(entityToSave, result);
        verify(kanbanBoardRepository).save(entityToSave);
    }

    @Test
    @DisplayName("Should throw a DatabaseFetchException.")
    void testSaveKanbanBoard_DATABASE_FETCH_EXCEPTION() {
        doThrow(new RuntimeException("Error!")).when(kanbanBoardRepository)
                .save(entityToSave);
        assertThrows(DatabaseFetchException.class,
                () -> commonKanbanBoardsService.saveKanbanBoard(entityToSave));
    }

    @Test
    @DisplayName("Should call the repository method.")
    void testConvertToEntityAndSaveKanbanBoard() throws DatabaseFetchException {
        KanbanBoardDTO dtoToSave = new KanbanBoardDTO().label("label");
        when(converter.convertOneKanbanBoardDTOToEntity(dtoToSave)).thenReturn(entityToSave);
        when(kanbanBoardRepository.save(entityToSave)).thenReturn(entityToSave);
        KanbanBoardEntity result = commonKanbanBoardsService.convertToEntityAndSaveKanbanBoard(dtoToSave);
        assertNotNull(result);
        verify(kanbanBoardRepository).save(any());
    }

    @Test
    @DisplayName("Should get the repository.")
    void testGetKanbanBoardRepository() {
        IKanbanBoardRepository i = commonKanbanBoardsService.getKanbanBoardRepository();
        assertNotNull(i);
    }

    @Test
    @DisplayName("Should get the converter.")
    void testGetConverter() {
        ConverterUtils c = commonKanbanBoardsService.getConverter();
        assertNotNull(c);
    }
}