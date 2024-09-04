package com.atmosware.library_project.api.controllers;

import com.atmosware.library_project.business.abstracts.CollectionService;
import com.atmosware.library_project.business.dtos.CollectionDTO;
import com.atmosware.library_project.business.dtos.CollectionResponse;
import com.atmosware.library_project.entities.Collection;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
@AllArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public Collection createCollection(@RequestBody CollectionDTO collectionDTO) {
        return collectionService.createCollection(collectionDTO);
    }
    @PostMapping("/{collectionId}/add-books")
    public void addBookToCollection(@PathVariable Long collectionId, @RequestParam List<Long> bookIds) {
        collectionService.addBooksToCollection(collectionId, bookIds);
    }

    @GetMapping("/user/{userId}")
    public List<CollectionResponse> getUserCollections(@PathVariable Long userId) {
        return collectionService.getUserCollections(userId);
    }
}
