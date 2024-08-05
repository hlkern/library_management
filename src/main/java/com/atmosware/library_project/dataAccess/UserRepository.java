package com.atmosware.library_project.dataAccess;

import com.atmosware.library_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
