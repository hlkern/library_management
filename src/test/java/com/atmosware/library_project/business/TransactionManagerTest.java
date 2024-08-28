package com.atmosware.library_project.business;

import com.atmosware.library_project.business.dtos.BookResponse;

import com.atmosware.library_project.business.concretes.TransactionManager;
import com.atmosware.library_project.business.dtos.TransactionRequest;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import com.atmosware.library_project.business.messages.BusinessMessages;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.dataAccess.TransactionRepository;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.Transaction;
import com.atmosware.library_project.entities.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionManagerTest {

    private TransactionManager transactionManager;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        bookRepository = Mockito.mock(BookRepository.class);
       // transactionManager = new TransactionManager(transactionRepository, userRepository, bookRepository);
    }

    @Test
    void borrowBook_ShouldBorrowBooksSuccessfully() {
        // Arrange
        TransactionRequest request = new TransactionRequest();
        request.setUserId(1L);
        request.setBookIds(Arrays.asList(1L, 2L));

        when(userRepository.existsById(1L)).thenReturn(true);

        // Mock kitaplar
        Book book1 = new Book();
        book1.setId(1L);
        book1.setStatus(Status.NEW);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setStatus(Status.NEW);

        // bookRepository.findById çağrılarının doğru bir şekilde mocklandığından emin olun
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book2));

        // Act
        TransactionResponse response = transactionManager.borrowBook(request);

        // Assert
        assertNotNull(response);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void borrowBook_ShouldThrowException_WhenBookAlreadyBorrowed() {
        // Arrange
        TransactionRequest request = new TransactionRequest();
        request.setUserId(1L);
        request.setBookIds(Collections.singletonList(1L));

        // Kullanıcının mevcut olduğunu mocklayın
        when(userRepository.existsById(1L)).thenReturn(true);

        // Ödünç alınmış bir kitap oluşturun
        Book borrowedBook = new Book();
        borrowedBook.setId(1L);
        borrowedBook.setStatus(Status.BORROWED);

        // bookRepository.findById çağrısının Optional.of(borrowedBook) döndürdüğünden emin olun
        when(bookRepository.findById(1L)).thenReturn(Optional.of(borrowedBook));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            transactionManager.borrowBook(request);
        });

        assertTrue(exception.getMessage().contains(BusinessMessages.ALREADY_BORROWED));
    }


    @Test
    void returnBook_ShouldReturnBooksSuccessfully() {
        // Arrange
        Long transactionId = 1L;
        List<Long> bookIds = Arrays.asList(1L, 2L);

        Transaction transaction = new Transaction();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setStatus(Status.BORROWED);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setStatus(Status.BORROWED);

        transaction.setBooks(Arrays.asList(book1, book2));

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book2));

        // Act
       // TransactionResponse response = transactionManager.returnBook(transactionId, bookIds);

        // Assert
        //assertNotNull(response);  // Bu satırın başarısız olmaması gerekiyor
        assertEquals(Status.RETURNED, transaction.getBooks().get(0).getStatus());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }


    @Test
    void returnBook_ShouldThrowException_WhenBookAlreadyReturned() {
        // Arrange
        Long transactionId = 1L;
        List<Long> bookIds = Collections.singletonList(1L);

        Transaction transaction = new Transaction();
        Book book = new Book();
        book.setId(1L);
        book.setStatus(Status.RETURNED);

        transaction.setBooks(Collections.singletonList(book));

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
          //  transactionManager.returnBook(transactionId, bookIds);
        });

        assertEquals(BusinessMessages.ALREADY_RETURNED, exception.getMessage());
    }

    @Test
    void getAll_ShouldReturnAllTransactions() {
        // Arrange
        Transaction transaction = new Transaction();
        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(transaction));

        // Act
        List<TransactionResponse> result = transactionManager.getAll();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void getBorrowedBooksByUserId_ShouldReturnBorrowedBooks() {
        // Arrange
        Long userId = 1L;
        Book book = new Book();
        book.setId(1L);
        book.setStatus(Status.BORROWED);

        when(userRepository.existsById(userId)).thenReturn(true);
        when(bookRepository.findBorrowedBooksByUserId(userId, Status.BORROWED)).thenReturn(Collections.singletonList(book));

        // Act
        List<BookResponse> result = transactionManager.getBorrowedBooksByUserId(userId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(Status.BORROWED, result.get(0).getStatus());
    }
}
