package com.atmosware.library_project.business.concretes;


import com.atmosware.library_project.business.abstracts.TransactionService;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.business.dtos.TransactionRequest;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import com.atmosware.library_project.business.messages.BusinessMessages;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.BookMapper;
import com.atmosware.library_project.core.utilities.mapping.TransactionMapper;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.dataAccess.TransactionRepository;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.Transaction;
import com.atmosware.library_project.entities.User;
import com.atmosware.library_project.entities.enums.BookStatus;
import com.atmosware.library_project.entities.enums.MembershipStatus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service("customTransactionManager")
@AllArgsConstructor
public class TransactionManager implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookManager bookManager;
    private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);

        @Override
        public TransactionResponse borrowBook(TransactionRequest transactionRequest) {

            User user = userRepository.findById(transactionRequest.getUserId())
                    .orElseThrow(() -> new BusinessException(BusinessMessages.USER_NOT_FOUND));

            if (user.getMembershipExpirationDate().isBefore(LocalDateTime.now())) {
                throw new BusinessException(BusinessMessages.MEMBERSHIP_EXPIRED);
            }

            if (user.getMembershipStatus() != MembershipStatus.ACTIVE) {
                throw new RuntimeException(BusinessMessages.MEMBERSHIP_IS_INACTIVE);
            }

            if (user.getOutstandingBalance() > 0) {
                throw new BusinessException(BusinessMessages.UNPAYED_DUES);
            }

            Transaction transaction = TransactionMapper.INSTANCE.mapToEntity(transactionRequest.getUserId(), transactionRequest);

            List<Book> books = transactionRequest.getBookIds().stream()
                    .map(bookId -> bookRepository.findById(bookId)
                            .orElseThrow(() -> new BusinessException(BusinessMessages.BOOK_NOT_FOUND)))
                    .map(bookRepository::save)
                    .collect(Collectors.toList());

            List<Book> alreadyBorrowedBooks = books.stream()
                    .filter(book -> book.getBookStatus() == BookStatus.BORROWED)
                    .toList();

            if (!alreadyBorrowedBooks.isEmpty()) {
                String borrowedBookNames = alreadyBorrowedBooks.stream()
                        .map(Book::getTitle)
                        .collect(Collectors.joining(", "));
                throw new BusinessException(BusinessMessages.ALREADY_BORROWED + borrowedBookNames);
            }

            books.forEach(book -> book.setBookStatus(BookStatus.BORROWED));

            transaction.setBooks(books);
            transaction.setCreatedDate(LocalDateTime.now());
            transaction.setBorrowDate(LocalDateTime.now());
            transaction.setDueDate(transaction.getBorrowDate().plusDays(30));
            transaction.setBookStatus(BookStatus.BORROWED);
            transaction.setLateFee(0.0);
            this.transactionRepository.save(transaction);

            logger.info("Book borrowing transaction with id: {} done successfully", transaction.getId());

            return TransactionMapper.INSTANCE.mapToResponse(transaction);
        }

    @Override
    public TransactionResponse returnBook(Long transactionId, List<Long> bookIds, List<Double> rates, List<String> comments) {

        Transaction transaction = this.transactionRepository.
                findById(transactionId).orElseThrow(() -> new BusinessException(BusinessMessages.TRANSACTION_NOT_FOUND));

        if (transaction.getBookStatus() == BookStatus.RETURNED) {
            throw new BusinessException(BusinessMessages.TRANSACTION_NOT_FOUND);
        }

        List<Book> books = bookIds.stream()
                .map(bookId -> this.bookRepository.findById(bookId)
                        .orElseThrow(() -> new BusinessException(BusinessMessages.BOOK_NOT_FOUND)))
                .toList();

        boolean anyBookAlreadyReturned = books.stream()
                .anyMatch(book -> book.getBookStatus() == BookStatus.RETURNED);

        if (anyBookAlreadyReturned) {
            throw new BusinessException(BusinessMessages.ALREADY_RETURNED);
        }

        long daysLate = DAYS.between(transaction.getDueDate(), LocalDateTime.now());
        double lateFee; //TODO iade ederken de ödeme yapılabilsin

        if (daysLate > 0) {
            lateFee = daysLate * 10.0;
            transaction.setLateFee(lateFee);

            User user = transaction.getUser();
            user.setOutstandingBalance(user.getOutstandingBalance() + lateFee);
            userRepository.save(user);
        }

        for (int i = 0; i < books.size(); i++) {
            books.get(i).setBookStatus(BookStatus.RETURNED);
            bookManager.updateRating(books.get(i).getId(), rates.get(i));
            bookManager.addComment(books.get(i).getId(), comments.get(i));
        }

        List<Book> allBooksInTransaction = transaction.getBooks();

        for (Book book : books) {
            if (!allBooksInTransaction.contains(book)) {
                allBooksInTransaction.add(book);
            }
        }

        transaction.setBooks(allBooksInTransaction);

        boolean allBooksReturned = transaction.getBooks().stream().allMatch(book -> book.getBookStatus() == BookStatus.RETURNED);
        if (allBooksReturned) {
            transaction.setBookStatus(BookStatus.RETURNED);
            transaction.setReturnDate(LocalDateTime.now());
        }

        Transaction updatedTransaction = this.transactionRepository.save(transaction);

        logger.info("Book returning transaction with id: {} done successfully", updatedTransaction.getId());

        return TransactionMapper.INSTANCE.mapToResponse(updatedTransaction);
    }

    @Override
    public List<TransactionResponse> getAll() {

        List<Transaction> transactions = this.transactionRepository.findAll();

        return TransactionMapper.INSTANCE.mapToResponseList(transactions);
    }

    @Override
    public List<BookResponse> getBorrowedBooksByUserId(Long userId) {

        checkIfUserExistsById(userId);

        List<Book> books = this.bookRepository.findBorrowedBooksByUserId(userId, BookStatus.BORROWED);

        return BookMapper.INSTANCE.mapToResponseList(books);
    }

    private void checkIfUserExistsById(Long userId) {

        if(!transactionRepository.existsById(userId)) {
            throw new BusinessException(BusinessMessages.USER_NOT_FOUND);
        }
    }

}
