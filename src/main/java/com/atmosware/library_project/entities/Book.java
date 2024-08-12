package com.atmosware.library_project.entities;

import com.atmosware.library_project.core.entities.BaseEntity;
import com.atmosware.library_project.entities.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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

    @Column(name = "status")
    private Status status;

    @ManyToMany(mappedBy = "books")
    private Set<Transaction> transactions = new HashSet<>();
}
