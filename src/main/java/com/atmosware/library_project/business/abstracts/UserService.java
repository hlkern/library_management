package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.RegisterRequest;
import com.atmosware.library_project.business.dtos.UserUpdateRequest;
import com.atmosware.library_project.business.dtos.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void register(RegisterRequest request);

    UserResponse update(UserUpdateRequest userUpdateRequest, Long id);

    List<String> getAllUserEmails();

    void renewMembership(Long id);
}
