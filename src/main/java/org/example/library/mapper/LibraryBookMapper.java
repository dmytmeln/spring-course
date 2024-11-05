package org.example.library.mapper;

import org.example.library.domain.LibraryBook;
import org.example.library.dto.LibraryBookDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryBookMapper {

    LibraryBookDto toDto(LibraryBook libraryBook);

    List<LibraryBookDto> toDto(List<LibraryBook> libraryBooks);

}
