package com.atmosware.library_project.core.utilities.mapping;

import com.atmosware.library_project.business.dtos.TransactionRequest;
import com.atmosware.library_project.business.dtos.TransactionResponse;
import com.atmosware.library_project.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionRequest toDTO(Transaction transaction);
    Transaction toEntity(TransactionRequest transactionRequest);
    List<TransactionResponse> mapToResponseList(List<Transaction> transactions);
}
