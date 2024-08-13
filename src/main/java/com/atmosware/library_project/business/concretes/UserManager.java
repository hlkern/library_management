package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.UserService;
import com.atmosware.library_project.business.dtos.RegisterRequest;
import com.atmosware.library_project.business.dtos.UserResponse;
import com.atmosware.library_project.business.dtos.UserUpdateRequest;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.UserMapper;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest registerRequest) {

        User user = UserMapper.INSTANCE.mapRegisterRequestToEntity(registerRequest);
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);
        user.setCreatedDate(LocalDateTime.now()); //TODO: register olurken role ile ol

        userRepository.save(user);
    }

    @Override
    public UserResponse update(UserUpdateRequest userUpdateRequest, Long id) {

        if(!userRepository.existsById(id)) {
            throw new BusinessException("User with id: " + id + " does not exist");
        }

        User dbUser = this.userRepository.findById(id).orElse(null);

        String encodedPassword = passwordEncoder.encode(userUpdateRequest.getPassword());
        dbUser.setPassword(encodedPassword);
        dbUser.setUsername(userUpdateRequest.getUsername());
        dbUser.setEmail(userUpdateRequest.getEmail());
        dbUser.setAuthorities(userUpdateRequest.getAuthorities());
        dbUser.setUpdatedDate(LocalDateTime.now());

        this.userRepository.save(dbUser);

        return UserMapper.INSTANCE.mapToResponse(dbUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findUserByEmail(username)
                .orElseThrow(() -> new BusinessException("Login failed"));
    }


}
