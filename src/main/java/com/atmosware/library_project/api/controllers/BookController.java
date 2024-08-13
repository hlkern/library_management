package com.atmosware.library_project.api.controllers;

import com.atmosware.library_project.business.abstracts.BookService;
import com.atmosware.library_project.business.dtos.BookRequest;
import com.atmosware.library_project.business.dtos.BookResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse add(@RequestBody BookRequest bookRequest) {

        return this.bookService.add(bookRequest);
    }

    @PutMapping("/update/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse update(@RequestBody BookRequest bookRequest, @PathVariable Long bookId) {

        return this.bookService.update(bookRequest, bookId);
    }

    @DeleteMapping("/delete/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long bookId) {

        this.bookService.delete(bookId);
    }

    @GetMapping("/getById/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse getById(@PathVariable Long bookId) {

        return this.bookService.getById(bookId);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAll() {

        return this.bookService.getAll();
    }

    @GetMapping("/getByCategory")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getByCategory(@RequestParam String category) {

        return this.bookService.getByCategory(category);
    }
}
