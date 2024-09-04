package com.atmosware.library_project.dataAccess;

import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.enums.BookStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByCategory(String category);

    @Query("SELECT b FROM Book b JOIN b.transactions t WHERE t.user.id = :userId AND b.bookStatus = :bookStatus")
    List<Book> findBorrowedBooksByUserId(@Param("userId") Long userId, @Param("bookStatus") BookStatus bookStatus);

    @Query("SELECT b FROM Book b JOIN b.transactions t GROUP BY b ORDER BY COUNT(t) DESC")
    List<Book> findMostBorrowedBooks(Pageable pageable);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.bookStatus = :bookStatus")
    long countByStatus(@Param("bookStatus") BookStatus bookStatus);

    // Kullanıcının 3.5 ve üzeri puan verdiği kitapların listesini almak için
    @Query("SELECT b FROM Book b JOIN b.transactions t WHERE t.user.id = :userId AND b.rating >= :minRating")
    List<Book> findBooksRatedByUserWithMinimumRating(@Param("userId") Long userId, @Param("minRating") double minRating);

    @Query("SELECT b FROM Book b WHERE b.author = :author AND b.rating >= :minRating")
    List<Book> findByAuthorAndMinimumRating(@Param("author") String author, @Param("minRating") Double minRating);

    @Query("SELECT b FROM Book b WHERE b.category = :category AND b.rating >= :minRating")
    List<Book> findByCategoryAndMinimumRating(@Param("category") String category, @Param("minRating") Double minRating);
}
