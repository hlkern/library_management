package com.atmosware.library_project.business;

import com.atmosware.library_project.business.concretes.AuthManager;
import com.atmosware.library_project.business.dtos.LoginRequest;
import com.atmosware.library_project.business.messages.BusinessMessages;
import com.atmosware.library_project.core.services.JwtService;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthManagerTest {

    private AuthManager authManager;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        jwtService = Mockito.mock(JwtService.class);
        authManager = new AuthManager(userRepository, authenticationManager, jwtService);
    }

    @Test
    void login_ShouldReturnJwt_WhenAuthenticationIsSuccessful() {
        // Arrange
        String email = "user@example.com";
        String password = "password";
        LoginRequest loginRequest = new LoginRequest(email, password);

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        User user = new User();
        user.setEmail(email);
        user.setUsername("testuser");
        user.setId(1L);
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        when(jwtService.generateToken(anyMap(), eq(email))).thenReturn("mocked-jwt-token");

        // Act
        String jwt = authManager.login(loginRequest);

        // Assert
        assertNotNull(jwt);
        assertEquals("mocked-jwt-token", jwt);
    }

    @Test
    void login_ShouldThrowException_WhenAuthenticationFails() {
        // Arrange
        String email = "user@example.com";
        String password = "wrongpassword";
        LoginRequest loginRequest = new LoginRequest(email, password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BusinessException(BusinessMessages.LOGIN_FAILED));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            authManager.login(loginRequest);
        });

        assertEquals(BusinessMessages.LOGIN_FAILED, exception.getMessage());
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        String email = "user@example.com";
        String password = "password";
        LoginRequest loginRequest = new LoginRequest(email, password);

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            authManager.login(loginRequest);
        });

        assertEquals(BusinessMessages.USER_NOT_FOUND, exception.getMessage());
    }
}
