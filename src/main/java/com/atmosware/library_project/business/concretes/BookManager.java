package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.BookService;
import com.atmosware.library_project.business.dtos.BookDTO;
import com.atmosware.library_project.core.utilities.mapping.BookMapper;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.entities.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookManager implements BookService {

    private BookRepository bookRepository;
    private BookMapper bookMapper;

    @Override
    public BookDTO getById(int id) {

        Book book = this.bookRepository.findById(id).orElse(null);

        return bookMapper.toDTO(book);
    }

    @Override
    public List<BookDTO> getAll() {

        List<Book> books = this.bookRepository.findAll();

        return books.stream().map(book -> this.bookMapper.toDTO(book)).collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {

        Book book = this.bookRepository.findById(id).orElse(null);

        book.setActive(false);
        book.setDeletedDate(LocalDateTime.now());
    }

    @Override
    public BookDTO update(BookDTO book) {
        return null;
    }

    @Override
    public BookDTO add(BookDTO book) {
        return null;
    }
}
