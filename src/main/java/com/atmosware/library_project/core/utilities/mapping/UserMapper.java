package com.atmosware.library_project.core.utilities.mapping;

import com.atmosware.library_project.business.dtos.UserRequest;
import com.atmosware.library_project.business.dtos.UserResponse;
import com.atmosware.library_project.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserRequest toDTO(User user);
    User toEntity(UserRequest userRequest);
    List<UserResponse> mapToResponseList(List<User> users);
}
