package com.simplejava.security.mapper;

import com.simplejava.security.dto.LoginRequest;
import com.simplejava.security.dto.SignupRequest;
import com.simplejava.security.model.EmployeeRole;
import com.simplejava.security.model.Role;
import com.simplejava.security.model.User;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 6/13/2025
 * Time: 9:45 PM
 */
public final class UserMapper {

    private UserMapper() {
        // private constructor to prevent instantiation
    }

    public static User mapToEntity(SignupRequest signupRequest) {

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());
        user.setEmail(signupRequest.getEmail());
        Set<Role> roles = signupRequest.getRoles().stream()
                .map(role -> new Role(EmployeeRole.valueOf(role)))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }

    public static User mapToEntity(LoginRequest loginRequest) {

        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        return user;
    }
}
