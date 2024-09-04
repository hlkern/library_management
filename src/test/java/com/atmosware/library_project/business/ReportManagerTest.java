package com.atmosware.library_project.business;

import com.atmosware.library_project.business.concretes.ReportManager;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import com.atmosware.library_project.business.messages.BusinessMessages;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.dataAccess.TransactionRepository;
import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.Transaction;
import com.atmosware.library_project.entities.enums.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportManagerTest {

    private ReportManager reportManager;
    private BookRepository bookRepository;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        bookRepository = Mockito.mock(BookRepository.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        reportManager = new ReportManager(bookRepository, transactionRepository);
    }

    @Test
    void getMostBorrowedBooks_ShouldReturnMostBorrowedBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Java Programming");

        Book book2 = new Book();
        book2.setTitle("Python Programming");

        Pageable pageable = PageRequest.of(0, 2);
        when(bookRepository.findMostBorrowedBooks(pageable)).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<BookResponse> result = reportManager.getMostBorrowedBooks(2);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Java Programming", result.get(0).getTitle());
        assertEquals("Python Programming", result.get(1).getTitle());
    }

    @Test
    void getUserHistory_ShouldReturnUserTransactionHistory() {
        // Arrange
        Long userId = 1L;
        Book book = new Book();
        book.setTitle("Java Programming");

        Transaction transaction1 = new Transaction();
        transaction1.setBooks(Arrays.asList(book));  // Books listesini doldur

        Transaction transaction2 = new Transaction();
        transaction2.setBooks(Arrays.asList(book));  // Books listesini doldur

        when(transactionRepository.existsById(userId)).thenReturn(true);
        when(transactionRepository.findByUserId(userId)).thenReturn(Arrays.asList(transaction1, transaction2));

        // Act
        List<TransactionResponse> result = reportManager.getUserHistory(userId);

        // Assert
        assertEquals(2, result.size());
    }


    @Test
    void getUserHistory_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        Long userId = 1L;

        when(transactionRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            reportManager.getUserHistory(userId);
        });

        assertEquals(BusinessMessages.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getStockInfo_ShouldReturnCorrectStockInfo() {
        // Arrange
        when(bookRepository.countByStatus(BookStatus.BORROWED)).thenReturn(5L);
        when(bookRepository.countByStatus(BookStatus.RETURNED)).thenReturn(10L);
        when(bookRepository.countByStatus(BookStatus.NEW)).thenReturn(15L);

        // Act
        Map<String, Long> result = reportManager.getStockInfo();

        // Assert
        assertEquals(5L, result.get("borrowed"));
        assertEquals(25L, result.get("available"));
        assertEquals(30L, result.get("total"));
    }
}
