package org.example.library.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "rating", nullable = false)
    private Byte rating;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATE")
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

}
