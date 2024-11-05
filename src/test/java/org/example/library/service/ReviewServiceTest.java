package org.example.library.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.library.domain.Review;
import org.example.library.dto.ReviewDto;
import org.example.library.factory.BookFactory;
import org.example.library.factory.UserFactory;
import org.example.library.mapper.ReviewMapper;
import org.example.library.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.example.library.factory.ReviewFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository repository;
    @Mock
    private ReviewMapper mapper;
    @Mock
    private UserService userService;
    @Mock
    private BookService bookService;
    @InjectMocks
    private ReviewService service;

    @Test
    public void givenBookIdAndReviewsInDb_whenGetBookReviews_thenReturnReviewDtoList() {
        var expectedDtos = setupMockedReviewListInDb();

        var actualDtos = service.getBookReviews(BookFactory.DEFAULT_ID);

        assertIterableEquals(expectedDtos, actualDtos);
    }

    private List<ReviewDto> setupMockedReviewListInDb() {
        var reviews = List.of(newDefaultReview());
        var dtos = List.of(newDefaultReviewDto());
        when(repository.findAllByBookId(BookFactory.DEFAULT_ID)).thenReturn(reviews);
        when(mapper.toDto(reviews)).thenReturn(dtos);
        return dtos;
    }

    @Test
    public void givenBookIdAndNoReviewsInDb_whenGetBookReviews_thenReturnEmptyReviewDtoList() {
        when(repository.findAllByBookId(BookFactory.DEFAULT_ID)).thenReturn(List.of());
        when(mapper.toDto(List.of())).thenReturn(List.of());

        var actualDtos = service.getBookReviews(BookFactory.DEFAULT_ID);

        assertTrue(actualDtos.isEmpty());
    }

    @Test
    public void givenUserIdBookIdAndDto_whenLeaveReview_thenReturnSavedReviewDto() {
        var inputDto = newDefaultReviewDtoWithoutId();
        var expectedDto = setupMockedLeaveReviewFlow(inputDto);

        var actualDto = service.leaveReview(UserFactory.DEFAULT_ID, BookFactory.DEFAULT_ID, inputDto);

        assertEquals(expectedDto, actualDto);
    }

    private ReviewDto setupMockedLeaveReviewFlow(ReviewDto dto) {
        var review = newDefaultReviewWithoutId();
        review.setBook(null);
        review.setUser(null);
        var savedReview = newDefaultReview();
        var savedReviewDto = newDefaultReviewDto();
        when(repository.existsByUserIdAndBookId(UserFactory.DEFAULT_ID, BookFactory.DEFAULT_ID)).thenReturn(false);
        when(mapper.toEntity(dto)).thenReturn(review);
        when(userService.getExistingUser(UserFactory.DEFAULT_ID)).thenReturn(review.getUser());
        when(bookService.getExistingBook(BookFactory.DEFAULT_ID)).thenReturn(review.getBook());
        when(repository.save(review)).thenReturn(savedReview);
        when(mapper.toDto(savedReview)).thenReturn(savedReviewDto);
        return savedReviewDto;
    }

    @Test
    public void givenExistingReview_whenUpdateReview_thenReturnUpdatedReviewDto() {
        var inputDto = newDefaultReviewDtoWithoutId();
        var expectedDto = setupMockedUpdateReviewFlow(inputDto);

        var actualDto = service.updateReview(DEFAULT_ID, BookFactory.DEFAULT_ID, inputDto);

        assertEquals(expectedDto, actualDto);
    }

    private ReviewDto setupMockedUpdateReviewFlow(ReviewDto dto) {
        var existingReview = newDefaultReview();
        var updatedReviewDto = newDefaultReviewDto();
        updatedReviewDto.setRating(dto.getRating());
        updatedReviewDto.setComment(dto.getComment());
        when(repository.findByIdAndBookId(DEFAULT_ID, BookFactory.DEFAULT_ID)).thenReturn(Optional.of(existingReview));
        when(repository.save(any(Review.class))).thenReturn(existingReview);
        when(mapper.toDto(existingReview)).thenReturn(updatedReviewDto);
        return updatedReviewDto;
    }

    @Test
    public void givenNonExistingReview_whenUpdateReview_thenThrowEntityNotFoundException() {
        when(repository.findByIdAndBookId(DEFAULT_ID, BookFactory.DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.updateReview(DEFAULT_ID, BookFactory.DEFAULT_ID, newDefaultReviewDtoWithoutId()));
    }

    @Test
    public void givenUserAlreadyLeftReview_whenLeaveReview_thenThrowIllegalArgumentException() {
        var inputDto = newDefaultReviewDtoWithoutId();
        when(repository.existsByUserIdAndBookId(UserFactory.DEFAULT_ID, BookFactory.DEFAULT_ID)).thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.leaveReview(UserFactory.DEFAULT_ID, BookFactory.DEFAULT_ID, inputDto));
    }

    @Test
    public void givenExistingReview_whenRemoveReview_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> service.removeReview(DEFAULT_ID, BookFactory.DEFAULT_ID));
    }

}
