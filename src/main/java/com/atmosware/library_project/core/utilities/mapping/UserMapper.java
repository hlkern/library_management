package com.atmosware.library_project.core.utilities.mapping;

import com.atmosware.library_project.business.dtos.RegisterRequest;
import com.atmosware.library_project.business.dtos.UserResponse;
import com.atmosware.library_project.business.dtos.UserUpdateRequest;
import com.atmosware.library_project.entities.Role;
import com.atmosware.library_project.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse mapToResponse(User user);
    List<UserResponse> mapToResponseList(List<User> users);

    @Mapping(target = "authorities", source = "role", qualifiedByName = "mapRoleToAuthorities")
    User toEntity(RegisterRequest registerRequest);

    @Named("mapRoleToAuthorities")
    default Set<Role> mapRoleToAuthorities(Role role) {
        return role == null ? Collections.emptySet() : Collections.singleton(role);
    }

    User mapResponseToEntity(UserResponse userResponse);
    User mapUpdateRequestToEntity(UserUpdateRequest updateRequest);

    @Mapping(target = "id", source = "userId")
    User userFromId(Long userId);
}
