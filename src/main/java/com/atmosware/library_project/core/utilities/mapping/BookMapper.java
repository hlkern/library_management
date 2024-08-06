package com.atmosware.library_project.core.utilities.mapping;

import com.atmosware.library_project.business.dtos.BookDTO;
import com.atmosware.library_project.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO toDTO(Book book);
    Book toEntity(BookDTO bookDTO);
}
