package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.UserService;
import com.atmosware.library_project.business.dtos.RegisterRequest;
import com.atmosware.library_project.business.dtos.UserResponse;
import com.atmosware.library_project.business.dtos.UserUpdateRequest;
import com.atmosware.library_project.business.messages.BusinessMessages;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.UserMapper;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);

    @Override
    public void register(RegisterRequest registerRequest) {

        checkIfUserExistsByUsername(registerRequest.getUsername());
        checkIfUserExistsByEmail(registerRequest.getEmail());

        User user = UserMapper.INSTANCE.toEntity(registerRequest);
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);
        user.setCreatedDate(LocalDateTime.now());
        user.setMembershipExpirationDate(LocalDateTime.now().plusYears(1));
        userRepository.save(user);

        logger.info("User with id: {} registered successfully", user.getId());
    }

    @Override
    public UserResponse update(UserUpdateRequest userUpdateRequest, Long id) {

        checkIfUserExistsByUsername(userUpdateRequest.getUsername());
        checkIfUserExistsByEmail(userUpdateRequest.getEmail());

        User dbUser = this.userRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessMessages.USER_NOT_FOUND));

        String encodedPassword = passwordEncoder.encode(userUpdateRequest.getPassword());
        dbUser.setPassword(encodedPassword);
        dbUser.setUsername(userUpdateRequest.getUsername());
        dbUser.setEmail(userUpdateRequest.getEmail());
        dbUser.setAuthorities(userUpdateRequest.getAuthorities());
        dbUser.setUpdatedDate(LocalDateTime.now());

        this.userRepository.save(dbUser);

        logger.info("User with id: {} updated successfully", dbUser.getId());

        return UserMapper.INSTANCE.mapToResponse(dbUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findUserByEmail(username)
                .orElseThrow(() -> new BusinessException(BusinessMessages.LOGIN_FAILED));
    }

    @Override
    public List<String> getAllUserEmails() {

        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    public void renewMembership(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found"));

        user.setMembershipExpirationDate(user.getMembershipExpirationDate().plusYears(1));
        userRepository.save(user);
    }

    public void payFees(Long userId, Double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessMessages.USER_NOT_FOUND));

        if (amount <= 0) {
            throw new BusinessException("Payment amount must be greater than zero.");
        }

        if (amount > user.getOutstandingBalance()) {
            throw new BusinessException("Payment amount exceeds outstanding balance.");
        }

        user.setOutstandingBalance(user.getOutstandingBalance() - amount);
        userRepository.save(user);

        logger.info("User with id: {} has paid {} towards outstanding fees.", userId, amount);
    }

    private void checkIfUserExistsByUsername(String username) {

        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new BusinessException(BusinessMessages.USER_ALREADY_EXISTS);
        }
    }

    private void checkIfUserExistsByEmail(String email) {

        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isPresent()) {
            throw new BusinessException(BusinessMessages.EMAIL_ALREADY_EXISTS);
        }
    }
}
