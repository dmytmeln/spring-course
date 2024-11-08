package org.example.library.service;

import org.example.library.domain.BookStatus;
import org.example.library.domain.Library;
import org.example.library.domain.LibraryBook;
import org.example.library.dto.LibraryBookDto;
import org.example.library.exception.NotFoundException;
import org.example.library.factory.BookFactory;
import org.example.library.mapper.LibraryBookMapper;
import org.example.library.repository.LibraryBookRepository;
import org.example.library.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.example.library.factory.LibraryBookFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonalLibraryServiceTest {

    @Mock
    private LibraryBookRepository repository;
    @Mock
    private LibraryBookMapper mapper;
    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private BookService bookService;
    @InjectMocks
    private PersonalLibraryService service;

    @Test
    public void givenUserId_whenGetAllLibraryBooks_thenReturnLibraryBookDtoList() {
        var expectedDtos = setupMockedLibraryBookList();

        var actualDtos = service.getAllLibraryBooks(DEFAULT_LIBRARY_ID);

        assertIterableEquals(expectedDtos, actualDtos);
    }

    private List<LibraryBookDto> setupMockedLibraryBookList() {
        var libraryBooks = List.of(newDefaultLibraryBook());
        var dtos = List.of(newDefaultLibraryBookDto());
        when(repository.findAllByLibraryId(DEFAULT_LIBRARY_ID)).thenReturn(libraryBooks);
        when(mapper.toDto(libraryBooks)).thenReturn(dtos);
        return dtos;
    }

    @Test
    public void givenUserIdAndBookId_whenAddBookToLibrary_thenReturnSavedLibraryBookDto() {
        var expectedDto = setupMockedAddBookToLibrary();

        var actualDto = service.addBookToLibrary(DEFAULT_LIBRARY_ID, BookFactory.DEFAULT_ID);

        assertEquals(expectedDto, actualDto);
    }

    private LibraryBookDto setupMockedAddBookToLibrary() {
        var libraryBook = newDefaultLibraryBook();
        var savedLibraryBook = newDefaultLibraryBook();
        var savedLibraryBookDto = newDefaultLibraryBookDto();
        when(libraryRepository.findById(DEFAULT_LIBRARY_ID)).thenReturn(Optional.of(mock(Library.class)));
        when(bookService.getExistingBook(BookFactory.DEFAULT_ID)).thenReturn(libraryBook.getBook());
        when(repository.save(any(LibraryBook.class))).thenReturn(savedLibraryBook);
        when(mapper.toDto(savedLibraryBook)).thenReturn(savedLibraryBookDto);
        return savedLibraryBookDto;
    }

    @Test
    public void givenNonExistingLibrary_whenAddBookToLibrary_thenThrowNotFoundException() {
        when(libraryRepository.findById(DEFAULT_LIBRARY_ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.addBookToLibrary(DEFAULT_LIBRARY_ID, BookFactory.DEFAULT_ID)
        );
    }

    @Test
    public void givenNonExistingBook_whenAddBookToLibrary_thenThrowNotFoundException() {
        when(bookService.getExistingBook(BookFactory.DEFAULT_ID)).thenThrow(NotFoundException.class);

        assertThrows(
                NotFoundException.class,
                () -> service.addBookToLibrary(DEFAULT_LIBRARY_ID, BookFactory.DEFAULT_ID)
        );
    }

    @Test
    public void givenUserIdBookIdAndStatus_whenChangeBookStatus_thenReturnUpdatedLibraryBookDto() {
        var expectedDto = setupMockedChangeBookStatus();

        var actualDto = service.changeBookStatus(DEFAULT_LIBRARY_ID, BookFactory.DEFAULT_ID, BookStatus.READ);

        assertEquals(expectedDto, actualDto);
    }

    private LibraryBookDto setupMockedChangeBookStatus() {
        var existingLibraryBook = newDefaultLibraryBook();
        var updatedLibraryBookDto = newDefaultLibraryBookDto();
        existingLibraryBook.setStatus(BookStatus.READ);
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(existingLibraryBook));
        when(repository.save(existingLibraryBook)).thenReturn(existingLibraryBook);
        when(mapper.toDto(existingLibraryBook)).thenReturn(updatedLibraryBookDto);
        return updatedLibraryBookDto;
    }

    @Test
    public void givenNonExistingLibraryBook_whenChangeBookStatus_thenThrowNotFoundException() {
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.changeBookStatus(DEFAULT_LIBRARY_ID, BookFactory.DEFAULT_ID, BookStatus.READ)
        );
    }

    @Test
    public void givenExistingLibraryBook_whenRemoveBookFromLibrary_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> service.removeBookFromLibrary(DEFAULT_LIBRARY_ID, BookFactory.DEFAULT_ID));
    }

}
