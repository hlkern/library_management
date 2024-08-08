package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.LoginRequest;

public interface AuthService {

    String login(LoginRequest loginRequest);
}
