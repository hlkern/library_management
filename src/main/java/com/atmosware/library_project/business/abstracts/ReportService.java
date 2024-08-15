package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.business.dtos.TransactionResponse;

import java.util.List;
import java.util.Map;

public interface ReportService {

    List<BookResponse> getMostBorrowedBooks(int numberOfMostBorrowedBooks);

    List<TransactionResponse> getUserHistory(Long userId);

    Map<String, Long> getStockInfo();
}
