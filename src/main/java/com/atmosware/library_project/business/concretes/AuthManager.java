package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.AuthService;
import com.atmosware.library_project.business.dtos.LoginRequest;
import com.atmosware.library_project.business.messages.BusinessMessages;
import com.atmosware.library_project.core.services.JwtService;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.User;
import com.atmosware.library_project.entities.enums.MembershipStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AuthManager implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if(!authentication.isAuthenticated())
            throw new BusinessException(BusinessMessages.LOGIN_FAILED);

        User user = this.userRepository.findUserByEmail(loginRequest.getEmail()).orElse(null);

        if (user.getMembershipExpirationDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException(BusinessMessages.MEMBERSHIP_EXPIRED);
        }

        if (user.getMembershipStatus() != MembershipStatus.ACTIVE) {
            throw new RuntimeException(BusinessMessages.MEMBERSHIP_IS_INACTIVE);
        }

        if(user == null){
            throw new BusinessException(BusinessMessages.USER_NOT_FOUND);
        }

        return generateJwt(user);
    }

    private String generateJwt(User user) {

        Map<String,Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("id",user.getId());
        return jwtService.generateToken(claims, user.getEmail());
    }
}
