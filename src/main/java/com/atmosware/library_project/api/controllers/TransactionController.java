package com.atmosware.library_project.api.controllers;

import com.atmosware.library_project.business.abstracts.TransactionService;
import com.atmosware.library_project.business.dtos.TransactionRequest;
import com.atmosware.library_project.business.dtos.TransactionResponse;
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
    public TransactionResponse borrow(@RequestBody TransactionRequest transactionRequest) {

        return this.transactionService.borrowBook(transactionRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getAll() {

        return this.transactionService.getAll();
    }
}
