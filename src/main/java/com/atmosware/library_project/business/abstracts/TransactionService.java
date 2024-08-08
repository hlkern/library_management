package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.TransactionRequest;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import java.util.List;

public interface TransactionService {

    TransactionResponse getById(int id);

    List<TransactionResponse> getAll();

    void delete(int id);

    TransactionResponse update(TransactionRequest transactionRequest);

    TransactionResponse add(TransactionRequest transactionRequest);
}
