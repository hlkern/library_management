package com.atmosware.library_project.dataAccess;

import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findByUserId(Long userId);

    @Query("SELECT c.books FROM Collection c WHERE c.id = :collectionId")
    List<Book> findBooksByCollectionId(@Param("collectionId") Long collectionId);
}
