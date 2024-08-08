package com.atmosware.library_project.business.dtos;

import com.atmosware.library_project.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    private String userName;
    private String email;
    private String password;
    private Role role;
}
