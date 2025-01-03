package org.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private Integer id;
    private String title;
    private Short publishYear;
    private String language;
    private Short pages;
    private String description;
    private List<AuthorDto> authors;
    private CategoryDto category;

    public Set<Integer> getAuthorsIds() {
        return authors.stream()
                .map(AuthorDto::getId)
                .collect(toSet());
    }

}
