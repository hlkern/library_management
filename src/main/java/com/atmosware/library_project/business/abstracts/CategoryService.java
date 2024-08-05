package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.entities.Category;
import java.util.List;

public interface CategoryService {

    Category getById(int id);

    List<Category> getAll();

    void delete(int id);

    Category update(Category category);

    Category add(Category category);
}
