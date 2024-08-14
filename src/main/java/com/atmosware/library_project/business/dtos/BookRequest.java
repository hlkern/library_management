package com.atmosware.library_project.business.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookRequest {

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String category;
}
