package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.UserRequest;
import com.atmosware.library_project.business.dtos.UserResponse;
import java.util.List;

public interface UserService {

    UserResponse getById(int id);

    List<UserResponse> getAll();

    void delete(int id);

    UserResponse update(UserRequest userRequest);

    UserResponse add(UserRequest userRequest);
}
