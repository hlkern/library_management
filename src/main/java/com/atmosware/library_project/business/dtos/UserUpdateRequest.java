package com.atmosware.library_project.business.dtos;

import com.atmosware.library_project.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {

    private String username;
    private String email;
    private String password;
    private Set<Role> authorities;
}
