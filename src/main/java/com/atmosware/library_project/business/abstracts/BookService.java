package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.BookRequest;
import com.atmosware.library_project.business.dtos.BookResponse;

import java.util.List;

public interface BookService {

    BookResponse getById(int id);

    List<BookResponse> getAll();

    void delete(int id);

    BookResponse update(BookRequest bookRequest, int id);

    BookResponse add(BookRequest bookRequest);
}
