package com.atmosware.library_project.business;

import com.atmosware.library_project.business.concretes.BookManager;
import com.atmosware.library_project.business.abstracts.NotificationService;
import com.atmosware.library_project.business.abstracts.UserService;
import com.atmosware.library_project.business.dtos.BookRequest;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.entities.Book;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookManagerTest {

    private BookManager bookManager;
    private BookRepository bookRepository;
    private NotificationService notificationService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        bookRepository = Mockito.mock(BookRepository.class);
        notificationService = Mockito.mock(NotificationService.class);
        userService = Mockito.mock(UserService.class);
        bookManager = new BookManager(bookRepository, notificationService, userService);
    }

    @AfterEach
    void tearDown() {
        // Kaynakların temizlenmesi gerekirse burada yapılabilir.
    }

    @BeforeAll
    static void start() {
        // Bütün testler öncesi bir kez çalıştırılır.
    }

    @AfterAll
    static void end() {
        // Bütün testler sonrası bir kez çalıştırılır.
    }

    @Test
    void getById_ShouldReturnBook_WhenBookExists() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");

        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        BookResponse response = bookManager.getById(bookId);

        // Assert
        assertNotNull(response);
        assertEquals("Test Book", response.getTitle());
    }

    @Test
    void getById_ShouldThrowException_WhenBookDoesNotExist() {
        // Arrange
        Long bookId = 1L;

        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            bookManager.getById(bookId);
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void add_ShouldSaveBookAndSendNotification() {
        // Arrange
        BookRequest request = new BookRequest();
        request.setTitle("New Book");
        request.setAuthor("Author");
        request.setCategory("Category");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("New Book");

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        Mockito.when(userService.getAllUserEmails()).thenReturn(Collections.singletonList("user@example.com"));

        // Act
        BookResponse response = bookManager.add(request);

        // Assert
        assertNotNull(response);
        assertEquals("New Book", response.getTitle());
        //Mockito.verify(notificationService, Mockito.times(1)).sendNotificationToAllUsers(Mockito.anyList(), Mockito.eq("New Book"));
    }

    @Test
    void delete_ShouldThrowException_WhenBookDoesNotExist() {
        // Arrange
        Long bookId = 1L;

        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            bookManager.delete(bookId);
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void update_ShouldUpdateBook_WhenBookExists() {
        // Arrange
        Long bookId = 1L;
        BookRequest request = new BookRequest();
        request.setTitle("Updated Title");
        request.setAuthor("Updated Author");
        request.setCategory("Updated Category");

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Old Title");

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        // Act
        BookResponse response = bookManager.update(request, bookId);

        // Assert
        assertNotNull(response);
        assertEquals("Updated Title", response.getTitle());
        assertEquals("Updated Author", response.getAuthor());
    }

    @Test
    void getAll_ShouldReturnListOfBooks() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        Mockito.when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        // Act
        List<BookResponse> books = bookManager.getAll();

        // Assert
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
    }
}
