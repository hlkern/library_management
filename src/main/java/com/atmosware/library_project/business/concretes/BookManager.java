package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.BookService;
import com.atmosware.library_project.business.abstracts.NotificationService;
import com.atmosware.library_project.business.dtos.BookRequest;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.BookMapper;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookManager implements BookService {

    private final BookRepository bookRepository;
    private final NotificationService notificationService;

    @Override
    public BookResponse getById(Long bookId) {

        if(!bookRepository.existsById(bookId)) {
            throw new BusinessException("Book with id: " + bookId + " does not exist"); //TODO : mesajları constant tutalım mı?
        }
        //TODO: kontrol işlemlerini managerların altında ayrı methodlarda yap
        Book book = this.bookRepository.findById(bookId).orElse(null);

        return BookMapper.INSTANCE.mapToResponse(book);
    }

    @Override
    public List<BookResponse> getAll() {

        List<Book> books = this.bookRepository.findAll();

        return BookMapper.INSTANCE.mapToResponseList(books);
    }

    @Override
    public void delete(Long bookId) {

        if(!bookRepository.existsById(bookId)) {
            throw new BusinessException("Book with id: " + bookId + " does not exist");
        }

        this.bookRepository.deleteById(bookId);
    }

    @Override
    public BookResponse update(BookRequest bookRequest, Long bookId) {

        if(!bookRepository.existsById(bookId)) {
            throw new BusinessException("Book with id: " + bookId + " does not exist");
        }

        Book dbBook = this.bookRepository.findById(bookId).orElse(null);
        dbBook.setTitle(bookRequest.getTitle());
        dbBook.setAuthor(bookRequest.getAuthor());
        dbBook.setCategory(bookRequest.getCategory());
        dbBook.setUpdatedDate(LocalDateTime.now());

        Book updatedBook = this.bookRepository.save(dbBook);

        return BookMapper.INSTANCE.mapToResponse(updatedBook);
    }

    @Override
    public BookResponse add(BookRequest bookRequest) {

        Book book = BookMapper.INSTANCE.mapToEntity(bookRequest);
        book.setCreatedDate(LocalDateTime.now());
        book.setStatus(Status.NEW);

        this.bookRepository.save(book);

//        String mail = "user@example.com"; //TODO yalnız oturum açan kullanıcıya mı mail gönderilecek yoksa tüm kullanıcılara mı
//        String subject = "The book has been borrowes";
//        String body = "Dear customer, you borrowed the book with ID" + book.getId();
//
//        notificationService.sendNotification(mail, subject, body);

        return BookMapper.INSTANCE.mapToResponse(book);
    }

    @Override
    public List<BookResponse> getByCategory(String category) {

        List<Book> books = this.bookRepository.findAllByCategory(category);

        return BookMapper.INSTANCE.mapToResponseList(books);
    }
}
