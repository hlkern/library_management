package com.atmosware.library_project.api.controllers;

import com.atmosware.library_project.business.abstracts.BookSearchService;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.core.utilities.mapping.BookMapper;
import com.atmosware.library_project.entities.Book;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class SearchController {
    private final BookSearchService bookSearchService;

    @GetMapping("/search")
    public List<BookResponse> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category) {

        List<Book> books = bookSearchService.searchBooks(title, author, category);
        return BookMapper.INSTANCE.mapToResponseList(books);
    }
}
