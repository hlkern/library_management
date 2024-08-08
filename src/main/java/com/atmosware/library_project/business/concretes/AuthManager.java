package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.AuthService;
import com.atmosware.library_project.business.abstracts.UserService;
import com.atmosware.library_project.business.dtos.LoginRequest;
import com.atmosware.library_project.business.dtos.UserResponse;
import com.atmosware.library_project.core.services.JwtService;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.UserMapper;
import com.atmosware.library_project.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class AuthManager implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if(!authentication.isAuthenticated())
            throw new BusinessException("Invalid email or password");

        UserResponse userResponse = userService.findByUsername(loginRequest.getEmail());
        User user = UserMapper.INSTANCE.mapResponseToEntity(userResponse);

        return generateJwt(user);
    }

    private String generateJwt(User user) {

        Map<String,Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("id",user.getId());
        return jwtService.generateToken(claims, user.getEmail());
    }
}
