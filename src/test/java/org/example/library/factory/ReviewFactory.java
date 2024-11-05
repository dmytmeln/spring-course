package org.example.library.factory;

import org.example.library.domain.Review;
import org.example.library.dto.ReviewDto;

import static org.example.library.factory.UserFactory.newDefaultUser;
import static org.example.library.factory.UserFactory.newDefaultUserResponse;

public class ReviewFactory {

    public static final Integer DEFAULT_ID = 100;
    public static final Byte DEFAULT_RATING = 5;
    public static final String DEFAULT_COMMENT = "Excellent book! Highly recommended.";

    public static Review newDefaultReview() {
        return Review.builder()
                .id(DEFAULT_ID)
                .rating(DEFAULT_RATING)
                .comment(DEFAULT_COMMENT)
                .user(newDefaultUser())
                .book(BookFactory.newDefaultBook())
                .build();
    }

    public static Review newDefaultReviewWithoutId() {
        return Review.builder()
                .rating(DEFAULT_RATING)
                .comment(DEFAULT_COMMENT)
                .build();
    }

    public static ReviewDto newDefaultReviewDto() {
        return ReviewDto.builder()
                .id(DEFAULT_ID)
                .user(newDefaultUserResponse())
                .rating(DEFAULT_RATING)
                .comment(DEFAULT_COMMENT)
                .build();
    }

    public static ReviewDto newDefaultReviewDtoWithoutId() {
        return ReviewDto.builder()
                .user(newDefaultUserResponse())
                .rating(DEFAULT_RATING)
                .comment(DEFAULT_COMMENT)
                .build();
    }
}
