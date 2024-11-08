package org.example.library.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.library.domain.BookStatus;
import org.example.library.dto.LibraryBookDto;
import org.example.library.security.UserDetailsImpl;
import org.example.library.service.PersonalLibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("api/library")
@RequiredArgsConstructor
public class PersonalLibraryController {

    private final PersonalLibraryService service;

    @GetMapping("/books")
    public ResponseEntity<List<LibraryBookDto>> getLibraryBooks(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ok(service.getAllLibraryBooks(userDetails.getUserId()));
    }

    @PostMapping("/books/{bookId}")
    public ResponseEntity<LibraryBookDto> addBookToLibrary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer bookId
    ) {
        var savedLibraryBookDto = service.addBookToLibrary(userDetails.getUserId(), bookId);
        var uri = ServletUriComponentsBuilder.fromPath("/api/library/books")
                .build()
                .toUri();
        return created(uri).body(savedLibraryBookDto);
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<LibraryBookDto> updateBookInLibrary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer bookId,
            @RequestParam BookStatus status
    ) {
        return ok(service.changeBookStatus(userDetails.getUserId(), bookId, status));
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBookFromLibrary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer bookId
    ) {
        service.removeBookFromLibrary(userDetails.getUserId(), bookId);
        return noContent().build();
    }

}
