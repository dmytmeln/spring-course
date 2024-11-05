package org.example.library.repository;

import org.example.library.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Optional<Review> findByIdAndBookId(Integer id, Integer bookId);

    List<Review> findAllByBookId(Integer bookId);

    boolean existsByUserIdAndBookId(Integer userId, Integer bookId);

    void deleteByIdAndBookId(Integer id, Integer bookId);

}
