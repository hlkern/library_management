package com.atmosware.library_project.entities;

import com.atmosware.library_project.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name= "author")
    private String author;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "units_in_stock")
    private int unitsInStock;
}
