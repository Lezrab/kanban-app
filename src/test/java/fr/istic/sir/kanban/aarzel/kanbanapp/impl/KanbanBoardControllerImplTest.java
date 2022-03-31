package fr.istic.sir.kanban.aarzel.kanbanapp.impl;

import fr.istic.sir.kanban.aarzel.kanbanapp.configuration.KanbanConfiguration;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.DatabaseFetchException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceAlreadyExistsException;
import fr.istic.sir.kanban.aarzel.kanbanapp.exception.ResourceNotFoundException;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.TestUtils;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.DeleteKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.GetKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.PostKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.boards.UpdateKanbanBoardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.cards.DeleteKanbanCardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.cards.GetKanbanCardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.cards.PostKanbanCardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.cards.UpdateKanbanCardsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.sections.DeleteKanbanSectionsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.sections.GetKanbanSectionsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.sections.PostKanbanSectionsService;
import fr.istic.sir.kanban.aarzel.kanbanapp.services.sections.UpdateKanbanSectionsService;
import fr.istic.sir.kanban.aarzel.model.KanbanBoardDTO;
import fr.istic.sir.kanban.aarzel.model.KanbanCardDTO;
import fr.istic.sir.kanban.aarzel.model.KanbanSectionDTO;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KanbanBoardControllerImplTest {
    @Mock
    private KanbanConfiguration kanbanConfiguration;
    @Mock
    private GetKanbanBoardsService getKanbanBoardsService;
    @Mock
    private PostKanbanBoardsService postKanbanBoardService;
    @Mock
    private DeleteKanbanBoardsService deleteKanbanBoardsService;
    @Mock
    private UpdateKanbanBoardsService updateKanbanBoardsService;
    @Mock
    private GetKanbanSectionsService getKanbanSectionsService;
    @Mock
    private PostKanbanSectionsService postKanbanSectionsService;
    @Mock
    private DeleteKanbanSectionsService deleteKanbanSectionsService;
    @Mock
    private UpdateKanbanSectionsService updateKanbanSectionsService;
    @Mock
    private GetKanbanCardsService getKanbanCardsService;
    @Mock
    private PostKanbanCardsService postKanbanCardsService;
    @Mock
    private DeleteKanbanCardsService deleteKanbanCardsService;
    @Mock
    private UpdateKanbanCardsService updateKanbanCardsService;
    @Mock
    private Logger loggerMock;
    @InjectMocks
    private KanbanBoardControllerImpl kanbanBoardControllerImpl;

    private final KanbanBoardDTO boardDto = TestUtils.nominalKanbanBoardDto();
    private final KanbanSectionDTO sectionDto = TestUtils.nominalKanbanSectionDto();
    private final KanbanCardDTO cardDto = TestUtils.nominalKanbanCardDto();

    @Test
    @DisplayName("Should return HTTP code 200 OK.")
    void testGetOneKanbanBoardById() throws DatabaseFetchException, ResourceNotFoundException {
        when(getKanbanBoardsService.getOneKanbanBoardById(any(), any()))
                .thenReturn(boardDto);
        ResponseEntity<KanbanBoardDTO> response = kanbanBoardControllerImpl
                .getOneKanbanBoardById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testGetOneKanbanBoardById_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(getKanbanBoardsService)
                .getOneKanbanBoardById(any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.getOneKanbanBoardById(1L));
    }

    @Test
    @DisplayName("Should return HTTP code 201 CREATED.")
    void testCreateOneKanbanBoardById() throws DatabaseFetchException {
        when(postKanbanBoardService.createOneKanbanBoardById(any(), any(), any()))
                .thenReturn(boardDto);
        ResponseEntity<KanbanBoardDTO> response = kanbanBoardControllerImpl
                .createOneKanbanBoardById(1L, boardDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testCreateOneKanbanBoardById_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException {
        doThrow(DatabaseFetchException.class).when(postKanbanBoardService)
                .createOneKanbanBoardById(any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.createOneKanbanBoardById(1L, boardDto));
    }


    @Test
    @DisplayName("Should return HTTP code 201 CREATED.")
    void testPutOneKanbanBoardById() throws DatabaseFetchException, ResourceNotFoundException {
        when(updateKanbanBoardsService.putOneKanbanBoardByExternalId(any(), any(), any()))
                .thenReturn(boardDto);
        ResponseEntity<KanbanBoardDTO> response = kanbanBoardControllerImpl
                .putOneKanbanBoardById(1L, boardDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testPutOneKanbanBoardById_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(updateKanbanBoardsService)
                .putOneKanbanBoardByExternalId(any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.putOneKanbanBoardById(1L, boardDto));
    }

    @Test
    @DisplayName("Should return HTTP code 204 NO_CONTENT.")
    void testDeleteOneKanbanBoardById() {
        ResponseEntity<Void> response = kanbanBoardControllerImpl
                .deleteOneKanbanBoardById(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testDeleteOneKanbanBoardById_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(deleteKanbanBoardsService)
                .deleteOneKanbanBoardByExternalId(any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.deleteOneKanbanBoardById(1L));
    }

    @Test
    @DisplayName("Should return HTTP code 200 OK.")
    void testGetManyKanbanBoards() throws DatabaseFetchException {
        when(getKanbanBoardsService.getManyKanbanBoards(any()))
                .thenReturn(List.of(boardDto));
        ResponseEntity<List<KanbanBoardDTO>> response = kanbanBoardControllerImpl
                .getManyKanbanBoards();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testGetManyKanbanBoards_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException {
        doThrow(DatabaseFetchException.class).when(getKanbanBoardsService)
                .getManyKanbanBoards(any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.getManyKanbanBoards());
    }

    @Test
    @DisplayName("Should return HTTP code 204 NO_CONTENT.")
    void testDeleteAllKanbanBoards() {
        ResponseEntity<Void> result = kanbanBoardControllerImpl.deleteAllKanbanBoards();
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testDeleteAllKanbanBoards_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException {
        doThrow(DatabaseFetchException.class).when(deleteKanbanBoardsService)
                .deleteAllKanbanBoards(any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.deleteAllKanbanBoards());
    }

    @Test
    @DisplayName("Should return HTTP code 200 OK.")
    void testGetKanbanSectionByKanbanBoard() throws DatabaseFetchException, ResourceNotFoundException {
        when(getKanbanSectionsService.getKanbanSectionByKanbanBoard(any(), any(), any())).thenReturn(sectionDto);
        ResponseEntity<KanbanSectionDTO> result = kanbanBoardControllerImpl.getKanbanSectionByKanbanBoard(1L, 1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testGetKanbanSectionByKanbanBoard_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(getKanbanSectionsService)
                .getKanbanSectionByKanbanBoard(any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.getKanbanSectionByKanbanBoard(1L, 1L));
    }

    @Test
    @DisplayName("Should return HTTP code 201 CREATED.")
    void testCreateOneKanbanSectionInKanbanBoard() throws ResourceAlreadyExistsException, DatabaseFetchException, ResourceNotFoundException {
        when(postKanbanSectionsService.createOneKanbanSectionInKanbanBoard(any(), any(), any())).thenReturn(sectionDto);
        ResponseEntity<KanbanSectionDTO> result = kanbanBoardControllerImpl.createOneKanbanSectionInKanbanBoard(1L, 1L, sectionDto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testCreateOneKanbanSectionInKanbanBoard_DATABASE_FETCH_EXCEPTION() throws ResourceAlreadyExistsException, DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(postKanbanSectionsService)
                .createOneKanbanSectionInKanbanBoard(any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.createOneKanbanSectionInKanbanBoard(1L, 1L, sectionDto));
    }

    @Test
    @DisplayName("Should return HTTP code 201 CREATED.")
    void testUpdateOneKanbanSectionInKanbanBoard() throws ResourceAlreadyExistsException, DatabaseFetchException, ResourceNotFoundException {
        when(updateKanbanSectionsService.updateOneKanbanSectionInKanbanBoard(any(), any(), any(), any())).thenReturn(sectionDto);
        ResponseEntity<KanbanSectionDTO> result = kanbanBoardControllerImpl.updateOneKanbanSectionInKanbanBoard(1L, 1L, sectionDto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testUpdateOneKanbanSectionInKanbanBoard_DATABASE_FETCH_EXCEPTION() throws ResourceAlreadyExistsException, DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(updateKanbanSectionsService)
                .updateOneKanbanSectionInKanbanBoard(any(), any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.updateOneKanbanSectionInKanbanBoard(1L, 1L, sectionDto));
    }

    @Test
    @DisplayName("Should return HTTP code 204 NO_CONTENT.")
    void testDeleteOneKanbanSectionInKanbanBoard() {
        ResponseEntity<Void> result = kanbanBoardControllerImpl.deleteOneKanbanSectionInKanbanBoard(1L, 1L);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testDeleteOneKanbanSectionInKanbanBoard_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(deleteKanbanSectionsService)
                .deleteOneKanbanSectionInKanbanBoard(any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.deleteOneKanbanSectionInKanbanBoard(1L, 1L));
    }

    @Test
    @DisplayName("Should return HTTP code 200 OK.")
    void testGetKanbanSectionsByKanbanBoard() throws DatabaseFetchException, ResourceNotFoundException {
        when(getKanbanSectionsService.getKanbanSectionsByKanbanBoard(any(), any())).thenReturn(List.of(sectionDto));
        ResponseEntity<List<KanbanSectionDTO>> result = kanbanBoardControllerImpl.getKanbanSectionsByKanbanBoard(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testGetKanbanSectionsByKanbanBoard_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(getKanbanSectionsService)
                .getKanbanSectionsByKanbanBoard(any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.getKanbanSectionsByKanbanBoard(1L));
    }


    @Test
    @DisplayName("Should return HTTP code 204 NO_CONTENT.")
    void testDeleteKanbanSectionsByKanbanBoard() {
        ResponseEntity<Void> result = kanbanBoardControllerImpl.deleteKanbanSectionsByKanbanBoard(1L);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testDeleteKanbanSectionsByKanbanBoard_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(deleteKanbanSectionsService)
                .deleteKanbanSectionsByKanbanBoard(any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.deleteKanbanSectionsByKanbanBoard(1L));
    }

    @Test
    @DisplayName("Should return HTTP code 200 OK.")
    void testGetOneKanbanCardByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        when(getKanbanCardsService.getOneKanbanCardByKanbanSection(any(), any(), any(), any())).thenReturn(cardDto);
        ResponseEntity<KanbanCardDTO> result = kanbanBoardControllerImpl.getOneKanbanCardByKanbanSection(1L, 1L, 1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testGetOneKanbanCardByKanbanSection_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(getKanbanCardsService)
                .getOneKanbanCardByKanbanSection(any(), any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.getOneKanbanCardByKanbanSection(1L, 1L, 1L));
    }

    @Test
    @DisplayName("Should return HTTP code 201 CREATED.")
    void testCreateOneKanbanCardByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        when(postKanbanCardsService.createOneKanbanCardByKanbanSection(any(), any(), any(), any(), any())).thenReturn(cardDto);
        ResponseEntity<KanbanCardDTO> result = kanbanBoardControllerImpl.createOneKanbanCardByKanbanSection(1L, 1L, 1L, new KanbanCardDTO());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testCreateOneKanbanCardByKanbanSection_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(postKanbanCardsService)
                .createOneKanbanCardByKanbanSection(any(), any(), any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.createOneKanbanCardByKanbanSection(1L, 1L, 1L, cardDto));
    }

    @Test
    @DisplayName("Should return HTTP code 201 CREATED.")
    void testUpdateOneKanbanCardByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        when(updateKanbanCardsService.updateOneKanbanCardByKanbanSection(any(), any(), any(), any(), any())).thenReturn(cardDto);
        ResponseEntity<KanbanCardDTO> result = kanbanBoardControllerImpl.updateOneKanbanCardByKanbanSection(1L, 1L, 1L, cardDto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testUpdateOneKanbanCardByKanbanSection_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(updateKanbanCardsService)
                .updateOneKanbanCardByKanbanSection(any(), any(), any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.updateOneKanbanCardByKanbanSection(1L, 1L, 1L, cardDto));
    }

    @Test
    @DisplayName("Should return HTTP code 204 NO_CONTENT.")
    void testDeleteOneKanbanCardByKanbanSection() {
        ResponseEntity<Void> result = kanbanBoardControllerImpl.deleteOneKanbanCardByKanbanSection(1L, 1L, 1L);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testDeleteOneKanbanCardByKanbanSection_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(deleteKanbanCardsService)
                .deleteOneKanbanCardByKanbanSection(any(), any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.deleteOneKanbanCardByKanbanSection(1L, 1L, 1L));
    }

    @Test
    @DisplayName("Should return HTTP code 200 OK.")
    void testGetKanbanCardsByKanbanSection() throws DatabaseFetchException, ResourceNotFoundException {
        when(getKanbanCardsService.getKanbanCardsByKanbanSection(any(), any(), any())).thenReturn(List.of(cardDto));
        ResponseEntity<List<KanbanCardDTO>> result = kanbanBoardControllerImpl.getKanbanCardsByKanbanSection(1L, 1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testGetKanbanCardsByKanbanSection_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(getKanbanCardsService)
                .getKanbanCardsByKanbanSection(any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.getKanbanCardsByKanbanSection(1L, 1L));
    }

    @Test
    @DisplayName("Should return HTTP code 204 NO_CONTENT.")
    void testDeleteKanbanCardsByKanbanSection() {
        ResponseEntity<Void> result = kanbanBoardControllerImpl.deleteKanbanCardsByKanbanSection(1L, 1L);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw an exception.")
    void testDeleteKanbanCardsByKanbanSection_DATABASE_FETCH_EXCEPTION() throws DatabaseFetchException, ResourceNotFoundException {
        doThrow(DatabaseFetchException.class).when(deleteKanbanCardsService)
                .deleteKanbanCardsByKanbanSection(any(), any(), any());
        assertThrows(DatabaseFetchException.class, () -> kanbanBoardControllerImpl.deleteKanbanCardsByKanbanSection(1L, 1L));
    }

}