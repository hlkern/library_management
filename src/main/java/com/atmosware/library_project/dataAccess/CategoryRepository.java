package com.atmosware.library_project.dataAccess;

import com.atmosware.library_project.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
