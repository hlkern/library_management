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

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse update(@RequestBody BookRequest bookRequest, @PathVariable int id) {

        return this.bookService.update(bookRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {

        this.bookService.delete(id);
    }

    @GetMapping("/getById{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse getById(@PathVariable int id) {

        return this.bookService.getById(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAll() {

        return this.bookService.getAll();
    }
}
