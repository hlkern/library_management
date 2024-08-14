package com.atmosware.library_project.dataAccess;

import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByCategory(String category);

    @Query("SELECT b FROM Book b JOIN b.transactions t WHERE t.user.id = :userId AND b.status = :status")
    List<Book> findBorrowedBooksByUserId(@Param("userId") Long userId, @Param("status") Status status);

    @Query("SELECT b FROM Book b JOIN b.transactions t GROUP BY b ORDER BY COUNT(t) DESC")
    List<Book> findMostBorrowedBooks(Pageable pageable);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.status = :status")
    long countByStatus(@Param("status") Status status);
}
