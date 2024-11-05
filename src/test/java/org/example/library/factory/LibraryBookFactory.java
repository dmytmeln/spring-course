package org.example.library.factory;

import org.example.library.domain.LibraryBook;
import org.example.library.domain.LibraryBookId;
import org.example.library.dto.LibraryBookDto;

public class LibraryBookFactory {

    public final static Integer DEFAULT_LIBRARY_ID = 50;
    public final static LibraryBookId DEFAULT_ID = new LibraryBookId(DEFAULT_LIBRARY_ID, BookFactory.DEFAULT_ID);

    public static LibraryBook newDefaultLibraryBook() {
        return LibraryBook.builder()
                .libraryId(DEFAULT_LIBRARY_ID)
                .bookId(BookFactory.DEFAULT_ID)
                .book(BookFactory.newDefaultBook())
                .build();
    }

    public static LibraryBookDto newDefaultLibraryBookDto() {
        return LibraryBookDto.builder()
                .book(BookFactory.newDefaultBookResponse())
                .build();
    }

}
