package com.atmosware.library_project.business;

import com.atmosware.library_project.business.concretes.UserManager;
import com.atmosware.library_project.business.dtos.RegisterRequest;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.Role;
import com.atmosware.library_project.entities.User;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserManagerTest {

    private UserManager userManager;
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userManager = new UserManager(userRepository, passwordEncoder);
    }

    @AfterEach
    void tearDown(){
    }
    @BeforeAll
    static void start(){
    }
    @AfterAll
    static void end(){
    }

    // TDD => Test Driven Development
    @Test
    void registerWithExistingUsername_ShouldThrowException() {

        Mockito.when(userRepository.findByUsername("azizalp")).thenReturn(Optional.of(new User()));

        RegisterRequest request = new RegisterRequest("azizalp", "aziz@gmail.com", "aziz12345",
                new Role("admin"));

        assertThrows(BusinessException.class, () -> userManager.register(request));
    }

    @Test
    void registerWithExistingEmail_ShouldThrowException() {

        Mockito.when(userRepository.findUserByEmail("aziz@gmail.com")).thenReturn(Optional.of(new User()));

        RegisterRequest request = new RegisterRequest("gruudd", "aziz@gmail.com", "aziz12345",
                new Role("admin"));

        assertThrows(BusinessException.class, () -> userManager.register(request));
    }

    @Test
    void registerSuccess() {

        User user = new User();
        user.setUsername("azizalp");
        user.setEmail("aziz@gmail.com");
        user.setPassword("aziz12345");

        Mockito.when(userRepository.findByUsername("azizalp")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByEmail("aziz@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        RegisterRequest request = new RegisterRequest("azizalp", "aziz@gmail.com", "aziz12345",
                new Role("admin"));

        userManager.register(request);

        assert true;
    }

}
