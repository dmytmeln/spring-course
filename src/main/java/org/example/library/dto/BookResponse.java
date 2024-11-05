package org.example.library.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookResponse {

    private Integer id;
    private String title;
    private Short publishYear;
    private String language;
    private String description;
    private List<AuthorDto> authors;
    private CategoryDto category;

}
