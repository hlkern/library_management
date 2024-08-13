package com.atmosware.library_project.api.controllers;

import com.atmosware.library_project.business.abstracts.UserService;
import com.atmosware.library_project.business.dtos.UserResponse;
import com.atmosware.library_project.business.dtos.UserUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PutMapping("/update/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse update(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable Long userId) {

        return userService.update(userUpdateRequest, userId);
    }
}
