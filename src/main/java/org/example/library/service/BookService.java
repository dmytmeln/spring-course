package org.example.library.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.library.domain.Book;
import org.example.library.dto.BookRequest;
import org.example.library.dto.BookResponse;
import org.example.library.mapper.BookMapper;
import org.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookMapper mapper;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public List<BookResponse> getAllBooks(Integer categoryId, String title) {
        List<Book> books;
        if (nonNull(categoryId)) {
            books = repository.findByTitleContainingAndCategoryId(title, categoryId);
        } else {
            books = repository.findByTitleContaining(title);
        }
        return mapper.toDto(books);
    }

    public List<BookResponse> getAuthorBooks(Integer authorId) {
        return mapper.toDto(repository.findByAuthorsId(authorId));
    }

    public Book getExistingBook(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книгу не знайдено!"));
    }

    public BookResponse getExistingBookDto(Integer id) {
        return mapper.toDto(getExistingBook(id));
    }

    public BookResponse createBook(BookRequest dto) {
        var book = mapper.toEntity(dto);
        setBookCategoryAndAuthors(dto, book);
        var savedBook = repository.save(book);
        return mapper.toDto(savedBook);
    }

    public BookResponse updateBook(Integer id, BookRequest dto) {
        requireExistsById(id);
        var book = mapper.toEntity(dto, id);
        setBookCategoryAndAuthors(dto, book);
        var updatedBook = repository.save(book);
        return mapper.toDto(updatedBook);
    }

    private void requireExistsById(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Книгу не знайдено!");
        }
    }

    private void setBookCategoryAndAuthors(BookRequest dto, Book book) {
        book.setCategory(categoryService.getExistingCategory(dto.getCategoryId()));
        book.setAuthors(
                dto.getAuthorsId().stream()
                        .map(authorService::getExistingAuthor)
                        .collect(toList())
        );
    }

    public void deleteBook(Integer id) {
        repository.deleteById(id);
    }

}
