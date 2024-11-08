package org.example.library.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.BookRequest;
import org.example.library.dto.BookResponse;
import org.example.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBooks(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false) Integer categoryId
    ) {
        return ok(service.getAllBooks(categoryId, title));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> addBook(@RequestBody @Valid BookRequest requestBody) {
        var createdBookResponse = service.createBook(requestBody);
        var uri = ServletUriComponentsBuilder.fromPath("/api/books/{bookId}")
                .buildAndExpand(createdBookResponse.getId())
                .toUri();
        return created(uri).body(createdBookResponse);
    }

    @GetMapping("/authors/{authorId}")
    public ResponseEntity<List<BookResponse>> getAuthorBooks(@PathVariable Integer authorId) {
        return ok(service.getAuthorBooks(authorId));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Integer bookId) {
        return ok(service.getExistingBookDto(bookId));
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Integer bookId,
            @RequestBody @Valid BookRequest requestBody
    ) {
        return ok(service.updateBook(bookId, requestBody));
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer bookId) {
        service.deleteBook(bookId);
        return noContent().build();
    }

}