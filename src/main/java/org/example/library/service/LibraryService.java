package org.example.library.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.library.domain.BookStatus;
import org.example.library.domain.Library;
import org.example.library.domain.LibraryBook;
import org.example.library.domain.LibraryBookId;
import org.example.library.dto.LibraryBookDto;
import org.example.library.mapper.LibraryBookMapper;
import org.example.library.repository.LibraryBookRepository;
import org.example.library.repository.LibraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryBookRepository repository;
    private final LibraryBookMapper mapper;
    private final LibraryRepository libraryRepository;
    private final BookService bookService;

    public List<LibraryBookDto> getAllLibraryBooks(Integer userId) {
        return mapper.toDto(repository.findAllByLibraryId(userId));
    }

    private Library getExistingLibrary(Integer id) {
        return libraryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Персональу бібліотеку не знайдено!"));
    }

    public LibraryBookDto addBookToLibrary(Integer userId, Integer bookId) {
        var savedLibraryBook = repository.save(
                LibraryBook.builder()
                        .book(bookService.getExistingBook(bookId))
                        .library(getExistingLibrary(userId))
                        .build()
        );
        return mapper.toDto(savedLibraryBook);
    }

    public LibraryBookDto changeStatus(Integer userId, Integer bookId, BookStatus status) {
        var existingLibraryBook = getExistingLibraryBook(userId, bookId);
        existingLibraryBook.setStatus(status);
        var updateLibraryBook = repository.save(existingLibraryBook);
        return mapper.toDto(updateLibraryBook);
    }

    private LibraryBook getExistingLibraryBook(Integer libraryId, Integer bookId) {
        return repository.findById(new LibraryBookId(libraryId, bookId))
                .orElseThrow(() -> new EntityNotFoundException("Книгу в персональній бібліотеці не знайдено!"));
    }

    public void removeBookFromLibrary(Integer userId, Integer bookId) {
        repository.deleteById(new LibraryBookId(userId, bookId));
    }

}
