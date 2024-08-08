package com.atmosware.library_project.business.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookRequest {

    private String title;
    private String author;
    private String category;
}
