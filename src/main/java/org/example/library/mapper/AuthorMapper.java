package org.example.library.mapper;

import org.example.library.domain.Author;
import org.example.library.dto.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    Author toEntity(AuthorDto dto);

    @Mapping(target = "id", source = "id")
    Author toEntity(AuthorDto dto, Integer id);

    AuthorDto toDto(Author author);

    List<AuthorDto> toDto(List<Author> authors);

}
