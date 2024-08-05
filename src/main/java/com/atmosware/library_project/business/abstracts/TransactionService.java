package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.entities.Transaction;
import java.util.List;

public interface TransactionService {

    Transaction getById(int id);

    List<Transaction> getAll();

    void delete(int id);

    Transaction update(Transaction transaction);

    Transaction add(Transaction transaction);
}
