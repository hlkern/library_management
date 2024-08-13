package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.TransactionRequest;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import java.util.List;

public interface TransactionService {

    TransactionResponse borrowBook(TransactionRequest transactionRequest);

    TransactionResponse returnBook(Long transactionId, List<Long> bookIds);

    List<TransactionResponse> getTransactionsByUserId(Long userId);

    List<TransactionResponse> getAll();
}
