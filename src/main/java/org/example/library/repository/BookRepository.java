package org.example.library.repository;

import org.example.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleContaining(String title);

    List<Book> findByTitleContainingAndCategoryId(String title, Integer categoryId);

    List<Book> findByAuthorsId(Integer authorId);

}
