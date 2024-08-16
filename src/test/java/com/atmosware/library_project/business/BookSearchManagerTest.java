package com.atmosware.library_project.business;

import com.atmosware.library_project.business.concretes.BookSearchManager;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookSearchManagerTest {

    private BookSearchManager bookSearchManager;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = Mockito.mock(BookRepository.class);
        bookSearchManager = new BookSearchManager(bookRepository);
    }

    @Test
    void searchBooks_ShouldReturnBooksMatchingTitle() {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Java Programming");
        Book book2 = new Book();
        book2.setTitle("Python Programming");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<Book> result = bookSearchManager.searchBooks("Java", null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java Programming", result.get(0).getTitle());
    }

    @Test
    void searchBooks_ShouldReturnBooksMatchingAuthor() {
        // Arrange
        Book book1 = new Book();
        book1.setAuthor("John Doe");
        Book book2 = new Book();
        book2.setAuthor("Jane Smith");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<Book> result = bookSearchManager.searchBooks(null, "Jane", null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Jane Smith", result.get(0).getAuthor());
    }

    @Test
    void searchBooks_ShouldReturnBooksMatchingCategory() {
        // Arrange
        Book book1 = new Book();
        book1.setCategory("Science Fiction");
        Book book2 = new Book();
        book2.setCategory("Fantasy");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<Book> result = bookSearchManager.searchBooks(null, null, "Fantasy");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Fantasy", result.get(0).getCategory());
    }

    @Test
    void searchBooks_ShouldReturnAllBooks_WhenNoFiltersProvided() {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Java Programming");
        book1.setAuthor("John Doe");
        book1.setCategory("Programming");

        Book book2 = new Book();
        book2.setTitle("Python Programming");
        book2.setAuthor("Jane Smith");
        book2.setCategory("Programming");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<Book> result = bookSearchManager.searchBooks(null, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void searchBooks_ShouldReturnEmptyList_WhenNoBooksMatch() {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Java Programming");
        book1.setAuthor("John Doe");
        book1.setCategory("Programming");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1));

        // Act
        List<Book> result = bookSearchManager.searchBooks("Python", "Jane", "Science");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
