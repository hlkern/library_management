package com.atmosware.library_project.core.utilities.mapping;

import com.atmosware.library_project.business.dtos.CategoryDTO;
import com.atmosware.library_project.business.dtos.TransactionDTO;
import com.atmosware.library_project.entities.Category;
import com.atmosware.library_project.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO toDTO(Transaction transaction);
    Transaction toEntity(TransactionDTO transactionDTO);
}
