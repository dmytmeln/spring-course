package org.example.library.factory;

import org.example.library.domain.Category;
import org.example.library.dto.CategoryDto;

public class CategoryFactory {

    public final static Integer DEFAULT_ID = 10;
    public final static String DEFAULT_NAME = "Fiction";
    public final static String DEFAULT_DESCRIPTION = "Fiction description";

    public static Category newDefaultCategory() {
        return Category.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .build();
    }

    public static Category newDefaultCategoryWithoutId() {
        return Category.builder()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .build();
    }

    public static CategoryDto newDefaultCategoryDto() {
        return CategoryDto.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .build();
    }

    public static CategoryDto newDefaultCategoryDtoWithoutId() {
        return CategoryDto.builder()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .build();
    }

}
