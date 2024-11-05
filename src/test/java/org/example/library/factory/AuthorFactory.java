package org.example.library.factory;

import org.example.library.domain.Author;
import org.example.library.dto.AuthorDto;

public class AuthorFactory {

    public static final Integer DEFAULT_ID = 10;
    public static final String DEFAULT_FULL_NAME = "John Doe";
    public static final Short DEFAULT_BIRTH_YEAR = 1940;
    public static final Short DEFAULT_DEATH_YEAR = 2024;
    public static final String DEFAULT_COUNTRY = "USA";

    public static Author newDefaultAuthor() {
        return Author.builder()
                .id(DEFAULT_ID)
                .fullName(DEFAULT_FULL_NAME)
                .birthYear(DEFAULT_BIRTH_YEAR)
                .deathYear(DEFAULT_DEATH_YEAR)
                .country(DEFAULT_COUNTRY)
                .build();
    }

    public static Author newDefaultAuthorWithoutId() {
        return Author.builder()
                .fullName(DEFAULT_FULL_NAME)
                .birthYear(DEFAULT_BIRTH_YEAR)
                .deathYear(DEFAULT_DEATH_YEAR)
                .country(DEFAULT_COUNTRY)
                .build();
    }

    public static AuthorDto newDefaultAuthorDto() {
        return AuthorDto.builder()
                .id(DEFAULT_ID)
                .fullName(DEFAULT_FULL_NAME)
                .birthYear(DEFAULT_BIRTH_YEAR)
                .deathYear(DEFAULT_DEATH_YEAR)
                .country(DEFAULT_COUNTRY)
                .build();
    }

    public static AuthorDto newDefaultAuthorDtoWithoutId() {
        return AuthorDto.builder()
                .fullName(DEFAULT_FULL_NAME)
                .birthYear(DEFAULT_BIRTH_YEAR)
                .deathYear(DEFAULT_DEATH_YEAR)
                .country(DEFAULT_COUNTRY)
                .build();
    }

}
