package com.atmosware.library_project.business.dtos;

import com.atmosware.library_project.entities.enums.Status;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String category;
    private Status status;
}
