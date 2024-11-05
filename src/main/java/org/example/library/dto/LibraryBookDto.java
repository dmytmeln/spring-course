package org.example.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LibraryBookDto {

    private BookResponse book;
    private String status;

}
