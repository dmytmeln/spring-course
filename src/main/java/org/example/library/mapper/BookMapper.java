package org.example.library.mapper;

import org.example.library.domain.Book;
import org.example.library.dto.BookRequest;
import org.example.library.dto.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "category", ignore = true)
    Book toEntity(BookRequest dto);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "category", ignore = true)
    Book toEntity(BookRequest dto, Integer id);

    BookResponse toDto(Book book);

    List<BookResponse> toDto(List<Book> book);

}
