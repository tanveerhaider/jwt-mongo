package com.simplejava.security.controller;

import com.simplejava.security.dto.JwtResponse;
import com.simplejava.security.dto.LoginRequest;
import com.simplejava.security.dto.SignupRequest;
import com.simplejava.security.mapper.UserMapper;
import com.simplejava.security.model.User;
import com.simplejava.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class handles user registration and login requests.
 * It uses PasswordEncoder to encode passwords during registration
 * and UserService to handle user-related operations.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final PasswordEncoder encoder;
    private final UserService userService;

    public AuthController(
            PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }
    /**
     * Register a new user with the provided signup request.
     * The password is encoded before saving the user.
     *
     * @param signUpRequest The signup request containing user details.
     * @return A ResponseEntity containing the saved user or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        // Encode the password
        signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
        User user = UserMapper.mapToEntity(signUpRequest);
        User savedUser = userService.registerUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }
    /**
     * Authenticate user and return a JWT token if successful.
     *
     * @param loginRequest The login request containing username and password.
     * @return A ResponseEntity containing the JWT response or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = UserMapper.mapToEntity(loginRequest);
        JwtResponse jwtResponse = userService.loginUser(user);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

    }


}
