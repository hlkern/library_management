package com.atmosware.library_project.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CollectionResponse {
    private Long id;
    private String name;
    private List<BookResponse> books;

}