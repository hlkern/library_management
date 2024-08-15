package com.atmosware.library_project.api.controllers;


import com.atmosware.library_project.business.abstracts.ReportService;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/mostborrowed/{numberOfMostBorrowedBooks}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getMostBorrowedBooks(@PathVariable int numberOfMostBorrowedBooks) {

        return this.reportService.getMostBorrowedBooks(numberOfMostBorrowedBooks);
    }

    @GetMapping("/userhistory/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getUserHistory(@PathVariable Long userId) {

        return this.reportService.getUserHistory(userId);
    }

    @GetMapping("/stock")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Long> getStockInfo() {

        return this.reportService.getStockInfo();
    }
}
