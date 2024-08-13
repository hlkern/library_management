package com.atmosware.library_project.dataAccess;

import com.atmosware.library_project.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
