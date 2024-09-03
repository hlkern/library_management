package com.atmosware.library_project.dataAccess;

import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t.books FROM Transaction t WHERE t.user.id = :userId")
    List<Book> findBorrowedBooksByUserId(@Param("userId") Long userId);

    List<Transaction> findByUserId(Long userId);
}
