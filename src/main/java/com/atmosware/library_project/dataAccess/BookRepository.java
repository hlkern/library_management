package com.atmosware.library_project.dataAccess;

import com.atmosware.library_project.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByCategory(String category);
}
