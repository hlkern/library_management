package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.entities.Book;

import java.util.List;

public interface BookService {

    Book getById(int id);

    List<Book> getAll();

    void delete(int id);

    Book update(Book book);

    Book add(Book book);
}
