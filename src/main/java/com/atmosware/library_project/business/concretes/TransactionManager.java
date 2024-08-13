package com.atmosware.library_project.business.concretes;


import com.atmosware.library_project.business.abstracts.TransactionService;
import com.atmosware.library_project.business.dtos.TransactionRequest;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.TransactionMapper;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.dataAccess.TransactionRepository;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.Transaction;
import com.atmosware.library_project.entities.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("customTransactionManager")
@AllArgsConstructor
public class TransactionManager implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public TransactionResponse borrowBook(TransactionRequest transactionRequest) {

        if(!userRepository.existsById(transactionRequest.getUserId())) {
            throw new BusinessException("User with id: " + transactionRequest.getUserId() + " does not exist");
        }

        Transaction transaction = TransactionMapper.INSTANCE.mapToEntity(transactionRequest.getUserId(), transactionRequest);

        List<Book> books = transactionRequest.getBookIds().stream()
                .map(bookId -> this.bookRepository.findById(bookId)
                        .orElseThrow(() -> new BusinessException("Book with id: " + bookId + " does not exist")))
                .peek(book -> book.setStatus(Status.BORROWED))
                .map(bookRepository::save)
                .collect(Collectors.toList());

        transaction.setBooks(books);
        transaction.setCreatedDate(LocalDateTime.now());
        transaction.setStatus(Status.BORROWED);
        this.transactionRepository.save(transaction);

        return TransactionMapper.INSTANCE.mapToResponse(transaction);
    }

    @Override
    public TransactionResponse returnBook(Long transactionId, List<Long> bookIds) {
        return null;
    }

    @Override
    public List<TransactionResponse> getTransactionsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<TransactionResponse> getAll() {

        List<Transaction> transactions = transactionRepository.findAll();

        return TransactionMapper.INSTANCE.mapToResponseList(transactions);
    }


}
