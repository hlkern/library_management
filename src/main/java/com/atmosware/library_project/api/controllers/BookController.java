package com.atmosware.library_project.api.controllers;

import com.atmosware.library_project.business.abstracts.BookService;
import com.atmosware.library_project.business.dtos.BookRequest;
import com.atmosware.library_project.business.dtos.BookResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse add(@Valid @RequestBody BookRequest bookRequest) {

        return this.bookService.add(bookRequest);
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse update(@Valid @RequestBody BookRequest bookRequest, @PathVariable Long bookId) {

        return this.bookService.update(bookRequest, bookId);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long bookId) {

        this.bookService.delete(bookId);
    }

    @GetMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse getById(@PathVariable Long bookId) {

        return this.bookService.getById(bookId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAll() {

        return this.bookService.getAll();
    }

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getByCategory(@RequestParam String category) {

        return this.bookService.getByCategory(category);
    }

    @PutMapping("/{bookId}/rate")
    @ResponseStatus(HttpStatus.OK)
    public void updateRating(@PathVariable Long bookId, @RequestParam double rating) {
        this.bookService.updateRating(bookId, rating);
    }
}
