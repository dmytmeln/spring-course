package org.example.library.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "book_libraries")
@IdClass(LibraryBookId.class)
public class LibraryBook {

    @Id
    @Column(name = "book_id", nullable = false, insertable = false, updatable = false)
    private Integer bookId;

    @Id
    @Column(name = "library_id", nullable = false, insertable = false, updatable = false)
    private Integer libraryId;

    @ManyToOne(optional = false)
    @MapsId
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookStatus status;

}
