package com.atmosware.library_project.core.utilities.mapping;

import com.atmosware.library_project.business.dtos.TransactionDTO;
import com.atmosware.library_project.business.dtos.UserDTO;
import com.atmosware.library_project.entities.Transaction;
import com.atmosware.library_project.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
