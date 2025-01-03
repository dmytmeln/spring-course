package org.example.library.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.ReviewDto;
import org.example.library.security.UserDetailsImpl;
import org.example.library.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getBookReviews(@RequestParam Integer bookId) {
        return ok(service.getBookReviews(bookId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ReviewDto leaveReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Integer bookId,
            @RequestBody @Valid ReviewDto requestBody
    ) {
        return service.leaveReview(userDetails.getUserId(), bookId, requestBody);
    }

    @PutMapping
    public ResponseEntity<ReviewDto> updateReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Integer bookId,
            @RequestBody @Valid ReviewDto requestBody
    ) {
        return ok(service.updateReview(userDetails.getUserId(), bookId, requestBody));
    }

    @DeleteMapping
    public ResponseEntity<ReviewDto> deleteReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Integer bookId
    ) {
        service.deleteReview(userDetails.getUserId(), bookId);
        return noContent().build();
    }

}
