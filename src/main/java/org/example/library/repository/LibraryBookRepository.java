package org.example.library.repository;

import org.example.library.domain.LibraryBook;
import org.example.library.domain.LibraryBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryBookRepository extends JpaRepository<LibraryBook, LibraryBookId> {

    List<LibraryBook> findAllByLibraryId(Integer libraryId);

}
