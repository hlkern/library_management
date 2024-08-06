package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.BookDTO;
import com.atmosware.library_project.entities.Book;

import java.util.List;

public interface BookService {

    BookDTO getById(int id);

    List<BookDTO> getAll();

    void delete(int id);

    BookDTO update(BookDTO bookDTO);

    BookDTO add(BookDTO bookDTO);
}
