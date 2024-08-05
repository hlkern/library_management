package com.atmosware.library_project.dataAccess;

import com.atmosware.library_project.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
