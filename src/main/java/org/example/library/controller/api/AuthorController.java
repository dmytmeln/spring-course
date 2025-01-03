package org.example.library.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.AuthorDto;
import org.example.library.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthor(
            @RequestParam(required = false, defaultValue = "") String fullName
    ) {
        return ok(service.getAllAuthors(fullName));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(CREATED)
    public AuthorDto addAuthor(@RequestBody @Valid AuthorDto requestBody) {
        return service.createAuthor(requestBody);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Integer authorId) {
        return ok(service.getExistingAuthorDto(authorId));
    }

    @PutMapping("/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDto> updateAuthor(
            @PathVariable Integer authorId,
            @RequestBody @Valid AuthorDto requestBody
    ) {
        return ok(service.updateAuthor(authorId, requestBody));
    }

    @DeleteMapping("/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer authorId) {
        service.deleteAuthor(authorId);
        return noContent().build();
    }

}
