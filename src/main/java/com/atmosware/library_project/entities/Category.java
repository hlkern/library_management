package com.atmosware.library_project.entities;

import com.atmosware.library_project.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Book> books = new ArrayList<>();
}
