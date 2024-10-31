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
@Table(name = "libraries")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "library_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "book_libraries",
            joinColumns = {@JoinColumn(name = "library_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<Book> books;

}