package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.BookSearchService;

import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.entities.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor

public class BookSearchManager implements BookSearchService {
    private final BookRepository bookRepository;
    @Override
    public List<Book> searchBooks(String title, String author, String category) {
        List<Book> books = bookRepository.findAll();

        if (title != null && !title.isEmpty()) {
            books = books.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (author != null && !author.isEmpty()) {
            books = books.stream()
                    .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (category != null && !category.isEmpty()) {
            books = books.stream()
                    .filter(book -> book.getCategory().toLowerCase().contains(category.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return books;
    }
}
