package com.atmosware.library_project.core.utilities.mapping;

import com.atmosware.library_project.business.dtos.TransactionRequest;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import com.atmosware.library_project.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UserMapper.class, BookMapper.class})
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "user", expression = "java(UserMapper.INSTANCE.userFromId(userId))")
    Transaction mapToEntity(Long userId, TransactionRequest transactionRequest);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "books", target = "bookIds")
    TransactionResponse mapToResponse(Transaction transaction);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "books", target = "bookIds")
    List<TransactionResponse> mapToResponseList(List<Transaction> transactions);
}
