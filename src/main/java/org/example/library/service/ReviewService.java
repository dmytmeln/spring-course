package org.example.library.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.library.domain.Review;
import org.example.library.dto.ReviewDto;
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

    private Review getExistingBookReview(Integer id, Integer bookId) {
        return repository.findByIdAndBookId(id, bookId)
                .orElseThrow(() -> new EntityNotFoundException("Відгук не знайдено!"));
    }

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
            throw new IllegalArgumentException("Не можна двічі залишити відгук на книгу!");
        }
    }

    public ReviewDto updateReview(Integer id, Integer bookId, ReviewDto dto) {
        var existingReview = getExistingBookReview(id, bookId);
        var updatedReview = repository.save(
                existingReview.toBuilder()
                        .rating(dto.getRating())
                        .comment(dto.getComment())
                        .build()
        );
        return mapper.toDto(updatedReview);
    }

    public void removeReview(Integer id, Integer bookId) {
        repository.deleteByIdAndBookId(id, bookId);
    }

}
