package org.example.library.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.AuthorDto;
import org.example.library.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

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
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody @Valid AuthorDto requestBody) {
        var createdAuthorResponse = service.createAuthor(requestBody);
        var uri = ServletUriComponentsBuilder.fromPath("/api/authors/{authorId}")
                .buildAndExpand(createdAuthorResponse.getId())
                .toUri();
        return created(uri).body(createdAuthorResponse);
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
