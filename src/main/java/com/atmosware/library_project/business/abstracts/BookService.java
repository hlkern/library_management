package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.BookRequest;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.entities.Book;

import java.util.List;

public interface BookService {

    BookResponse getById(Long bookId);

    List<BookResponse> getAll();

    void delete(Long bookId);

    BookResponse update(BookRequest bookRequest, Long bookId);

    BookResponse add(BookRequest bookRequest);

    List<BookResponse> getByCategory(String category);
}
