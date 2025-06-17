package com.simplejava.security.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 6/10/2025
 * Time: 10:27 PM
 */
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
