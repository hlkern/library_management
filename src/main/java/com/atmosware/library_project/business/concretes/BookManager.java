package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.BookService;
import com.atmosware.library_project.business.abstracts.NotificationService;
import com.atmosware.library_project.business.abstracts.UserService;
import com.atmosware.library_project.business.dtos.BookRequest;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.business.messages.BusinessMessages;
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
    private final UserService userService;

    @Override
    public BookResponse getById(Long bookId) {

        checkIfBookExistsById(bookId);

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

        checkIfBookExistsById(bookId);

        this.bookRepository.deleteById(bookId);
    }

    @Override
    public BookResponse update(BookRequest bookRequest, Long bookId) {

        Book dbBook = this.bookRepository.findById(bookId).orElseThrow(() -> new BusinessException(BusinessMessages.BOOK_NOT_FOUND));
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

        List<String> userEmails = userService.getAllUserEmails();

        notificationService.sendNotificationToAllUsers(userEmails, book.getTitle());

        return BookMapper.INSTANCE.mapToResponse(book);
    }

    @Override
    public List<BookResponse> getByCategory(String category) {

        List<Book> books = this.bookRepository.findAllByCategory(category);

        return BookMapper.INSTANCE.mapToResponseList(books);
    }

    private void checkIfBookExistsById(Long bookId) {

        if(!bookRepository.existsById(bookId)) {
            throw new BusinessException(BusinessMessages.BOOK_NOT_FOUND);
        }
    }
}
