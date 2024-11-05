package org.example.library.mapper;

import org.example.library.domain.Category;
import org.example.library.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDto dto);

    @Mapping(target = "id", source = "id")
    Category toEntity(CategoryDto dto, Integer id);

    CategoryDto toDto(Category category);

    List<CategoryDto> toDto(List<Category> categories);

}
