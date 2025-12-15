package com.hcc.services;

import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service layer for user-related operations.
 *
 * Provides an abstraction over {@link UserRepository} to encapsulate
 * user-related business logic and data access.
 * This allows controllers and other services to interact with user data without directly
 * depending on the repository layer.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
