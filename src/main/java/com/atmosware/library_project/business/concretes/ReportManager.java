package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.ReportService;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import com.atmosware.library_project.business.messages.BusinessMessages;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.BookMapper;
import com.atmosware.library_project.core.utilities.mapping.TransactionMapper;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.dataAccess.TransactionRepository;
import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.Transaction;
import com.atmosware.library_project.entities.enums.BookStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportManager implements ReportService {

    private final BookRepository bookRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public List<BookResponse> getMostBorrowedBooks(int numberOfMostBorrowedBooks) {

        Pageable pageable = PageRequest.of(0, numberOfMostBorrowedBooks);
        List<Book> books = this.bookRepository.findMostBorrowedBooks(pageable);

        return BookMapper.INSTANCE.mapToResponseList(books);
    }

    @Override
    public List<TransactionResponse> getUserHistory(Long userId) {

        checkIfUserExistsById(userId);

        List<Transaction> transactions = this.transactionRepository.findByUserId(userId);

        return TransactionMapper.INSTANCE.mapToResponseList(transactions);
    }

    @Override
    public Map<String, Long> getStockInfo() {

        long borrowedCount = this.bookRepository.countByStatus(BookStatus.BORROWED);
        long returnedCount = this.bookRepository.countByStatus(BookStatus.RETURNED);
        long newCount = this.bookRepository.countByStatus(BookStatus.NEW);
        long availableCount = returnedCount + newCount;

        Map<String, Long> statusCounts = new HashMap<>();
        statusCounts.put("borrowed", borrowedCount);
        statusCounts.put("available", availableCount);
        statusCounts.put("total", borrowedCount + availableCount);

        return statusCounts;
    }

    private void checkIfUserExistsById(Long userId) {

        if(!transactionRepository.existsById(userId)) {
            throw new BusinessException(BusinessMessages.USER_NOT_FOUND);
        }
    }

}
