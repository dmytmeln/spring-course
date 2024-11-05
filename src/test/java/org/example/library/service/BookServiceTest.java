package org.example.library.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.library.dto.BookRequest;
import org.example.library.dto.BookResponse;
import org.example.library.factory.AuthorFactory;
import org.example.library.factory.CategoryFactory;
import org.example.library.mapper.BookMapper;
import org.example.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.example.library.factory.AuthorFactory.newDefaultAuthor;
import static org.example.library.factory.BookFactory.*;
import static org.example.library.factory.BookFactory.newDefaultBookWithoutId;
import static org.example.library.factory.CategoryFactory.newDefaultCategory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository repository;
    @Mock
    private BookMapper mapper;
    @Mock
    private AuthorService authorService;
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private BookService service;

    @Test
    public void givenBooksInDb_whenGetAllBooks_thenReturnBookResponseList() {
        var expectedResponses = setupMockedBookListInDb_whenGetAll();

        var actualResponses = service.getAllBooks(null, "");

        assertIterableEquals(expectedResponses, actualResponses);
    }

    private List<BookResponse> setupMockedBookListInDb_whenGetAll() {
        var books = List.of(newDefaultBook());
        var responses = List.of(newDefaultBookResponse());
        when(repository.findByTitleContaining("")).thenReturn(books);
        when(mapper.toDto(books)).thenReturn(responses);
        return responses;
    }

    @Test
    public void givenBooksInDb_whenGetAllBooksByCategoryId_thenReturnBookResponseList() {
        var expectedResponses = setupMockedBookListInDb_whenGetAllWithCategoryId();

        var actualResponses = service.getAllBooks(CategoryFactory.DEFAULT_ID, "");

        assertIterableEquals(expectedResponses, actualResponses);
    }

    private List<BookResponse> setupMockedBookListInDb_whenGetAllWithCategoryId() {
        var books = List.of(newDefaultBook());
        var responses = List.of(newDefaultBookResponse());
        when(repository.findByTitleContainingAndCategoryId("", CategoryFactory.DEFAULT_ID)).thenReturn(books);
        when(mapper.toDto(books)).thenReturn(responses);
        return responses;
    }

    @Test
    public void givenBooksInDb_whenGetAuthorBooks_thenReturnBookResponseList() {
        var expectedResponses = setupMockedBookListInDb_whenGetAuthorBooks();

        var actualResponses = service.getAuthorBooks(AuthorFactory.DEFAULT_ID);

        assertIterableEquals(expectedResponses, actualResponses);
    }

    private List<BookResponse> setupMockedBookListInDb_whenGetAuthorBooks() {
        var books = List.of(newDefaultBook());
        var responses = List.of(newDefaultBookResponse());
        when(repository.findByAuthorsId(AuthorFactory.DEFAULT_ID)).thenReturn(books);
        when(mapper.toDto(books)).thenReturn(responses);
        return responses;
    }

    @Test
    public void givenNoBooksInDb_whenGetAuthorBooks_thenReturnEmptyBookResponseList() {
        when(repository.findByAuthorsId(AuthorFactory.DEFAULT_ID)).thenReturn(List.of());
        when(mapper.toDto(List.of())).thenReturn(List.of());

        var actualResponses = service.getAuthorBooks(AuthorFactory.DEFAULT_ID);

        assertTrue(actualResponses.isEmpty());
    }

    @Test
    public void givenExistingId_whenGetBook_thenReturnBook() {
        var expectedBook = newDefaultBook();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(expectedBook));

        var actualBook = service.getExistingBook(DEFAULT_ID);

        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void givenNotExistingId_whenGetBook_thenThrowEntityNotFoundException() {
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.getExistingBook(DEFAULT_ID)
        );
    }

    @Test
    public void givenExistingId_whenGetBookResponse_thenReturnResponse() {
        var expectedResponse = setupMockedBookInDbMappedToResponse();

        var actualResponse = service.getExistingBookDto(DEFAULT_ID);

        assertEquals(expectedResponse, actualResponse);
    }

    private BookResponse setupMockedBookInDbMappedToResponse() {
        var book = newDefaultBook();
        var response = newDefaultBookResponse();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(book));
        when(mapper.toDto(book)).thenReturn(response);
        return response;
    }

    @Test
    public void givenNotExistingId_whenGetBookDto_thenThrowEntityNotFoundException() {
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.getExistingBookDto(DEFAULT_ID)
        );
    }

    @Test
    public void givenBookRequest_whenCreateBook_thenReturnSavedBookResponse() {
        var inputRequest = newDefaultBookRequest();
        var expectedResponse = setupMockedCreationFlow(inputRequest);

        var actualResponse = service.createBook(inputRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    private BookResponse setupMockedCreationFlow(BookRequest request) {
        var book = newDefaultBookWithoutId();
        var savedBook = newDefaultBook();
        var savedResponse = newDefaultBookResponse();
        when(mapper.toEntity(request)).thenReturn(book);
        when(authorService.getExistingAuthor(AuthorFactory.DEFAULT_ID)).thenReturn(newDefaultAuthor());
        when(categoryService.getExistingCategory(CategoryFactory.DEFAULT_ID)).thenReturn(newDefaultCategory());
        when(repository.save(book)).thenReturn(savedBook);
        when(mapper.toDto(savedBook)).thenReturn(savedResponse);
        return savedResponse;
    }

    @Test
    public void givenNotExistingCategory_whenCreateBook_thenThrowEntityNotFoundException() {
        var request = newDefaultBookRequest();
        when(mapper.toEntity(request)).thenReturn(newDefaultBookWithoutId());
        when(categoryService.getExistingCategory(CategoryFactory.DEFAULT_ID)).thenThrow(EntityNotFoundException.class);

        assertThrows(
                EntityNotFoundException.class,
                () -> service.createBook(request));
    }

    @Test
    public void givenNotExistingAuthor_whenCreateBook_thenThrowEntityNotFoundException() {
        var request = newDefaultBookRequest();
        when(mapper.toEntity(request)).thenReturn(newDefaultBookWithoutId());
        when(authorService.getExistingAuthor(AuthorFactory.DEFAULT_ID)).thenThrow(EntityNotFoundException.class);

        assertThrows(
                EntityNotFoundException.class,
                () -> service.createBook(request));
    }

    @Test
    public void givenExistingIdAndBookRequest_whenUpdateBook_thenReturnUpdatedBookResponse() {
        var inputRequest = newDefaultBookRequest();
        var expectedResponse = setupMockedUpdateFlow(inputRequest);

        var actualResponse = service.updateBook(DEFAULT_ID, inputRequest);

        assertEquals(expectedResponse, actualResponse);
        assertEquals(DEFAULT_ID, actualResponse.getId());
    }

    private BookResponse setupMockedUpdateFlow(BookRequest request) {
        var existingBook = newDefaultBook();
        var updatedBook = newDefaultBook();
        var updatedResponse = newDefaultBookResponse();
        when(repository.existsById(DEFAULT_ID)).thenReturn(true);
        when(mapper.toEntity(request, DEFAULT_ID)).thenReturn(existingBook);
        when(authorService.getExistingAuthor(AuthorFactory.DEFAULT_ID)).thenReturn(newDefaultAuthor());
        when(categoryService.getExistingCategory(CategoryFactory.DEFAULT_ID)).thenReturn(newDefaultCategory());
        existingBook.setId(DEFAULT_ID);
        when(repository.save(existingBook)).thenReturn(updatedBook);
        when(mapper.toDto(updatedBook)).thenReturn(updatedResponse);
        return updatedResponse;
    }

    @Test
    public void givenNonExistingId_whenUpdateBook_thenThrowIllegalArgumentException() {
        var inputRequest = newDefaultBookRequest();
        when(repository.existsById(DEFAULT_ID)).thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateBook(DEFAULT_ID, inputRequest)
        );
    }

    @Test
    public void givenNotExistingCategory_whenUpdateBook_thenThrowEntityNotFoundException() {
        var request = newDefaultBookRequest();
        setupMockedUpdateFlowToThrowException_whenCategoryDoesntExist(request);

        assertThrows(
                EntityNotFoundException.class,
                () -> service.updateBook(DEFAULT_ID, request));
    }

    private void setupMockedUpdateFlowToThrowException_whenCategoryDoesntExist(BookRequest request) {
        when(repository.existsById(DEFAULT_ID)).thenReturn(true);
        when(mapper.toEntity(request, DEFAULT_ID)).thenReturn(newDefaultBook());
        when(categoryService.getExistingCategory(CategoryFactory.DEFAULT_ID)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    public void givenNotExistingAuthor_whenUpdateBook_thenThrowEntityNotFoundException() {
        var request = newDefaultBookRequest();
        setupMockedUpdateFlowToThrowException_whenAuthorDoesntExist(request);

        assertThrows(
                EntityNotFoundException.class,
                () -> service.updateBook(DEFAULT_ID, request));
    }

    private void setupMockedUpdateFlowToThrowException_whenAuthorDoesntExist(BookRequest request) {
        when(repository.existsById(DEFAULT_ID)).thenReturn(true);
        when(mapper.toEntity(request, DEFAULT_ID)).thenReturn(newDefaultBook());
        when(categoryService.getExistingCategory(CategoryFactory.DEFAULT_ID)).thenReturn(newDefaultCategory());
        when(authorService.getExistingAuthor(AuthorFactory.DEFAULT_ID)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    public void givenAnyId_whenDeleteBook_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> service.deleteBook(DEFAULT_ID));
        assertDoesNotThrow(() -> service.deleteBook(-1));
    }

}
