package com.atmosware.library_project.core.utilities.mapping;

import com.atmosware.library_project.business.dtos.BookRequest;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookResponse mapToResponse(Book book);
    Book mapToEntity(BookRequest bookRequest);
    List<BookResponse> mapToResponseList(List<Book> books);

    default List<Long> mapBookIds(List<Book> books) {
        return books.stream()
                .map(Book::getId)
                .collect(Collectors.toList());
    }
}
