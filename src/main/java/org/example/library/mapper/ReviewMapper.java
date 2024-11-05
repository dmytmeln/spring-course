package org.example.library.mapper;

import org.example.library.domain.Review;
import org.example.library.dto.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    Review toEntity(ReviewDto dto);

    ReviewDto toDto(Review review);

    List<ReviewDto> toDto (List<Review> reviews);

}
