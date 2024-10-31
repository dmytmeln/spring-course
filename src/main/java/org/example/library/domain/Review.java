package org.example.library.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static java.time.LocalDate.now;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDate createdAt;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    private void onCreate() {
        createdAt = now();
    }

}
