package com.atmosware.library_project.api.controllers;

import com.atmosware.library_project.business.abstracts.AuthService;
import com.atmosware.library_project.business.abstracts.UserService;
import com.atmosware.library_project.business.dtos.LoginRequest;
import com.atmosware.library_project.business.dtos.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequest registerRequest) {

        this.userService.register(registerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody LoginRequest loginRequest) {

        return this.authService.login(loginRequest);
    }
}
