package org.example.library.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publish_year", nullable = false)
    private Short publishYear;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "pages", nullable = false)
    private Short pages;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = {@JoinColumn(name = "book_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "author_id", nullable = false)}
    )
    private List<Author> authors;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany
    @JoinColumn(name = "book_id")
    private List<Review> reviews;

}
