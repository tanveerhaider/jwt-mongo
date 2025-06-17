package com.simplejava.security.service;

import com.simplejava.security.dto.JwtResponse;
import com.simplejava.security.exception.BadDataException;
import com.simplejava.security.jwt.JwtService;
import com.simplejava.security.model.Role;
import com.simplejava.security.model.User;
import com.simplejava.security.repository.RoleRepository;
import com.simplejava.security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;


/**
 * Description :
 * User: Tanveer Haider
 * Date: 6/11/2025
 * Time: 11:19 PM
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository; // Assuming roleRepository is not used in this service, otherwise initialize it
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user after validating the username and email.
     * Checks if the user already exists by username or email.
     * Validates that all roles assigned to the user are valid.
     *
     * @param user The user to register
     * @throws BadDataException if the user already exists or has invalid roles
     */
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadDataException("E01", "User with username '" + user.getUsername() + "' already exists.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadDataException("E01", "User with email '" + user.getEmail() + "' already exists.");
        }
        Set<Role> roles = user.getRoles();
        List<Role> masterList = roleRepository.findAll();
        List<Role> badRoles = roles.stream()
                .filter(role -> masterList.stream()
                        .noneMatch(masterRole -> masterRole.getName().equals(role.getName())))
                .toList();
        List<Role> updatedRoles = masterList.stream()
                .filter(masterRole -> roles.stream()
                        .anyMatch(role -> role.getName().equals(masterRole.getName())))
                .toList();
        if (!badRoles.isEmpty()) {
            String badRoleNames = badRoles.stream()
                    .map(Role::getName)
                    .map(Enum::name)
                    .collect(joining(", "));
            throw new BadDataException("E02", "User has bad roles: " + badRoleNames);
        }
        user.setRoles(new HashSet<>(updatedRoles));
        // If all checks pass, save the user
        return userRepository.save(user);
    }
    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param user The user to authenticate
     * @return JwtResponse containing the JWT token and user details
     */
    public JwtResponse loginUser(User user) {
        // Authenticate the user with the provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(),
                        user.getPassword()));
        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token based on the authentication
        String jwt = jwtService.generateJwtToken(authentication);

        // Get user details from the authentication object
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Extract user roles into a list
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

    }
}
