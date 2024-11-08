package org.example.library.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.library.domain.Review;
import org.example.library.dto.ReviewDto;
import org.example.library.exception.BadRequestException;
import org.example.library.exception.NotFoundException;
import org.example.library.mapper.ReviewMapper;
import org.example.library.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final ReviewMapper mapper;
    private final UserService userService;
    private final BookService bookService;

    public List<ReviewDto> getBookReviews(Integer bookId) {
        return mapper.toDto(repository.findAllByBookId(bookId));
    }

    @Transactional
    public ReviewDto leaveReview(Integer userId, Integer bookId, ReviewDto dto) {
        requireReviewNotExists(userId, bookId);
        var review = mapper.toEntity(dto);
        review.setUser(userService.getExistingUser(userId));
        review.setBook(bookService.getExistingBook(bookId));
        var savedReview = repository.save(review);
        return mapper.toDto(savedReview);
    }

    private void requireReviewNotExists(Integer userId, Integer bookId) {
        if (repository.existsByUserIdAndBookId(userId, bookId)) {
            throw new BadRequestException("Не можна двічі залишити відгук на книгу!");
        }
    }

    public ReviewDto updateReview(Integer userId, Integer bookId, ReviewDto dto) {
        var existingReview = getExistingBookReview(userId, bookId);
        var updatedReview = repository.save(
                existingReview.toBuilder()
                        .rating(dto.getRating())
                        .comment(dto.getComment())
                        .build()
        );
        return mapper.toDto(updatedReview);
    }

    private Review getExistingBookReview(Integer userId, Integer bookId) {
        return repository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new NotFoundException("Відгук не знайдено!"));
    }

    public void deleteReview(Integer userId, Integer bookId) {
        repository.deleteByUserIdAndBookId(userId, bookId);
    }

}
