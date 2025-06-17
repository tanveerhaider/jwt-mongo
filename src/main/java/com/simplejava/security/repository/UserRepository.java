package com.simplejava.security.repository;

import com.simplejava.security.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 6/9/2025
 * Time: 11:37 PM
 */
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByUsername(String username);
    /**
     * Finds a user by their email.
     *
     * @param username the email of the user to find
     * @return an Optional containing the User if found, or empty if not found
     */
    Boolean existsByUsername(String username);
    /**
     * Checks if a user exists by their email.
     *
     * @param email the email of the user to check
     * @return true if a user with the given email exists, false otherwise
     */
    Boolean existsByEmail(String email);

}
