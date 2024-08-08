package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.UserService;
import com.atmosware.library_project.business.dtos.RegisterRequest;
import com.atmosware.library_project.business.dtos.UserResponse;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.UserMapper;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest registerRequest) {

        User user = UserMapper.INSTANCE.mapRequestToEntity(registerRequest);
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    @Override
    public UserResponse findByUsername(String username) {

        User user = userRepository.findUserByEmail(username).orElseThrow();

        return UserMapper.INSTANCE.mapToResponse(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findUserByEmail(username)
                .orElseThrow(() -> new BusinessException("Login failed"));
    }
}
