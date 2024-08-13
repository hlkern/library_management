package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.entities.Book;

import java.util.List;

public interface BookSearchService {
    List<Book> searchBooks(String title, String author, String category);

}
