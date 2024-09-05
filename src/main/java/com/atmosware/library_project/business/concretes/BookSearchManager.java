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
        return bookRepository.searchBooks(title, author, category);
    }
}
