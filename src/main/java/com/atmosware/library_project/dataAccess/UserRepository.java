package com.atmosware.library_project.dataAccess;

import com.atmosware.library_project.entities.User;
import com.atmosware.library_project.entities.enums.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAllByEmailPermissionTrueAndMembershipStatus(MembershipStatus status);

}
