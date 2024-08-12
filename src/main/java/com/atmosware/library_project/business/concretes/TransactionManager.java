package com.atmosware.library_project.business.concretes;


import com.atmosware.library_project.business.abstracts.TransactionService;
import com.atmosware.library_project.business.dtos.TransactionRequest;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.TransactionMapper;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.dataAccess.TransactionRepository;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.Transaction;
import com.atmosware.library_project.entities.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
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

        Transaction transaction = new Transaction();
        transaction.setCreatedDate(LocalDateTime.now());
        transaction.setStatus(Status.BORROWED);
        return null;
    }

    @Override
    public TransactionResponse returnBook(int transactionId, List<Integer> bookIds) {
        return null;
    }

    @Override
    public List<TransactionResponse> getTransactionsByUserId(int userId) {
        return List.of();
    }
}
