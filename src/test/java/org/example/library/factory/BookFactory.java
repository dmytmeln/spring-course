package org.example.library.factory;

import org.example.library.domain.Book;
import org.example.library.dto.BookRequest;
import org.example.library.dto.BookResponse;

import java.util.List;

import static org.example.library.factory.AuthorFactory.newDefaultAuthor;
import static org.example.library.factory.CategoryFactory.newDefaultCategory;

public class BookFactory {
    public static final Integer DEFAULT_ID = 1;
    public static final String DEFAULT_TITLE = "Sample Book";
    public static final Short DEFAULT_PUBLISH_YEAR = 2023;
    public static final String DEFAULT_LANGUAGE = "English";
    public static final Short DEFAULT_PAGES = 300;
    public static final String DEFAULT_DESCRIPTION = "Sample book description.";

    public static Book newDefaultBook() {
        return Book.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .publishYear(DEFAULT_PUBLISH_YEAR)
                .language(DEFAULT_LANGUAGE)
                .pages(DEFAULT_PAGES)
                .description(DEFAULT_DESCRIPTION)
                .authors(List.of(newDefaultAuthor()))
                .category(newDefaultCategory())
                .build();
    }

    public static Book newDefaultBookWithoutId() {
        return Book.builder()
                .title(DEFAULT_TITLE)
                .publishYear(DEFAULT_PUBLISH_YEAR)
                .language(DEFAULT_LANGUAGE)
                .pages(DEFAULT_PAGES)
                .description(DEFAULT_DESCRIPTION)
                .authors(List.of(newDefaultAuthor()))
                .category(newDefaultCategory())
                .build();
    }

    public static BookRequest newDefaultBookRequest() {
        return BookRequest.builder()
                .title(DEFAULT_TITLE)
                .publishYear(DEFAULT_PUBLISH_YEAR)
                .language(DEFAULT_LANGUAGE)
                .pages(DEFAULT_PAGES)
                .description(DEFAULT_DESCRIPTION)
                .authorsId(List.of(AuthorFactory.DEFAULT_ID))
                .categoryId(CategoryFactory.DEFAULT_ID)
                .build();
    }

    public static BookResponse newDefaultBookResponse() {
        return BookResponse.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .publishYear(DEFAULT_PUBLISH_YEAR)
                .language(DEFAULT_LANGUAGE)
                .description(DEFAULT_DESCRIPTION)
                .authors(List.of(AuthorFactory.newDefaultAuthorDto()))
                .category(CategoryFactory.newDefaultCategoryDto())
                .build();
    }
}