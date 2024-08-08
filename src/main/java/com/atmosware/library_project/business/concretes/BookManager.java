package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.BookService;
import com.atmosware.library_project.business.dtos.BookRequest;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.BookMapper;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.entities.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookManager implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponse getById(int id) {

        if(!bookRepository.existsById(id)) {
            throw new BusinessException("Book with id: " + id + " does not exist"); //TODO : mesajları constant tutalım mı?
        }

        Book book = this.bookRepository.findById(id).orElse(null);

        return BookMapper.INSTANCE.mapToResponse(book);
    }

    @Override
    public List<BookResponse> getAll() {

        List<Book> books = this.bookRepository.findAll();

        return BookMapper.INSTANCE.mapToResponseList(books);
    }

    @Override
    public void delete(int id) {

        if(!bookRepository.existsById(id)) {
            throw new BusinessException("Book with id: " + id + " does not exist");
        }

        this.bookRepository.deleteById(id);
    }

    @Override
    public BookResponse update(BookRequest bookRequest, int id) {

        if(!bookRepository.existsById(id)) {
            throw new BusinessException("Book with id: " + id + " does not exist");
        }

        Book book = BookMapper.INSTANCE.mapToEntity(bookRequest);
        book.setUpdatedDate(LocalDateTime.now());

        this.bookRepository.save(book);

        return BookMapper.INSTANCE.mapToResponse(book);
    }

    @Override
    public BookResponse add(BookRequest bookRequest) {

        Book book = BookMapper.INSTANCE.mapToEntity(bookRequest);
        book.setCreatedDate(LocalDateTime.now());

        this.bookRepository.save(book);

        return BookMapper.INSTANCE.mapToResponse(book);
    }
}
