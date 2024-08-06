package com.atmosware.library_project.core.utilities.mapping;

import com.atmosware.library_project.business.dtos.BookDTO;
import com.atmosware.library_project.business.dtos.CategoryDTO;
import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
