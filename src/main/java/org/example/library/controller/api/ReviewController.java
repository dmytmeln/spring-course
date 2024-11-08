package org.example.library.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.ReviewDto;
import org.example.library.security.UserDetailsImpl;
import org.example.library.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/books/{bookId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getBookReviews(@PathVariable Integer bookId) {
        return ok(service.getBookReviews(bookId));
    }

    @PostMapping
    public ResponseEntity<ReviewDto> leaveReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer bookId,
            @RequestBody @Valid ReviewDto requestBody
    ) {
        var reviewDto = service.leaveReview(userDetails.getUserId(), bookId, requestBody);
        var uri = ServletUriComponentsBuilder.fromPath("/api/books/{bookId}/reviews/{reviewId}")
                .buildAndExpand(bookId, reviewDto.getId())
                .toUri();
        return created(uri).body(reviewDto);
    }

    @PutMapping
    public ResponseEntity<ReviewDto> updateReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer bookId,
            @RequestBody @Valid ReviewDto requestBody
    ) {
        return ok(service.updateReview(userDetails.getUserId(), bookId, requestBody));
    }

    @DeleteMapping
    public ResponseEntity<ReviewDto> deleteReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer bookId
    ) {
        service.deleteReview(userDetails.getUserId(), bookId);
        return noContent().build();
    }

}
