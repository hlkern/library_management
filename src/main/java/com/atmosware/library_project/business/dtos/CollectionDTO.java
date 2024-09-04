package com.atmosware.library_project.business.dtos;

import lombok.Data;
import java.util.List;

@Data
public class CollectionDTO {
    private Long id;
    private String name;
    private Long userId;
    private List<Long> bookIds; // Koleksiyona eklenecek kitapların ID'leri
}
