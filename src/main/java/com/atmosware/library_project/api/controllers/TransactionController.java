package com.atmosware.library_project.api.controllers;

import com.atmosware.library_project.business.abstracts.TransactionService;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.business.dtos.TransactionRequest;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse borrowBook(@Valid @RequestBody TransactionRequest transactionRequest) {

        return this.transactionService.borrowBook(transactionRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getAll() {

        return this.transactionService.getAll();
    }

    @PutMapping("/return/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse returnBook(@PathVariable Long transactionId, @RequestParam List<Long> bookIds) {

        return this.transactionService.returnBook(transactionId, bookIds);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getBorrowedBooksByUserId(@PathVariable Long userId) {

        return this.transactionService.getBorrowedBooksByUserId(userId);
    }

}
