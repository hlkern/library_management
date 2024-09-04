package com.atmosware.library_project.business.dtos;

import com.atmosware.library_project.entities.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookResponse {

    private String title;
    private String author;
    private String category;
    private BookStatus bookStatus;
    private double rating;
    private List<String> comments;
}
