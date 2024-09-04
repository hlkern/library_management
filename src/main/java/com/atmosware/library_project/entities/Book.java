package com.atmosware.library_project.entities;

import com.atmosware.library_project.core.entities.BaseEntity;
import com.atmosware.library_project.entities.enums.BookStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "category")
    private String category;

    @Column(name = "bookStatus")
    private BookStatus bookStatus;

    @ManyToMany(mappedBy = "books")
    private Set<Transaction> transactions = new HashSet<>();

    @Column(name = "rating")
    private Double rating = 0.0;

    @Column(name = "rating_count")
    private Integer ratingCount = 0;

    @ElementCollection
    @CollectionTable(name = "book_comments", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "comment")
    private List<String> comments = new ArrayList<>();
}
