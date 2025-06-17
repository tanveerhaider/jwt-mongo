package com.simplejava;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 6/10/2025
 * Time: 11:52 PM
 */
@RestController // Indicate that this class is a REST controller
@RequestMapping("/api/test")
public class TestController {
    /**
     * Public endpoint that can be accessed without any authentication.
     *
     * @return A string message indicating public content.
     */
    @GetMapping("/all") // Map GET requests to "/api/test/all"
    public String allAccess() {
        return "Public Content."; // Return a message accessible by anyone
    }
    /**
     * Endpoint accessible only to users with USER, MODERATOR, or ADMIN roles.
     *
     * @return A string message indicating user content.
     */
    @GetMapping("/user") // Map GET requests to "/api/test/user"
    @PreAuthorize("hasRole('USER') or " + // Require USER, MODERATOR, or ADMIN role
            "hasRole('MODERATOR') or " +
            "hasRole('ADMIN')")
    public String userAccess() {
        return "User Content."; // Return a message accessible by users with the required roles
    }
    /**
     * Endpoint accessible only to users with the MODERATOR role.
     *
     * @return A string message indicating moderator board content.
     */
    @GetMapping("/mod") // Map GET requests to "/api/test/mod"
    @PreAuthorize("hasRole('MODERATOR')") // Require MODERATOR role
    public String moderatorAccess() {
        return "Moderator Board."; // Return a message accessible by moderators
    }
    /**
     * Endpoint accessible only to users with the ADMIN role.
     *
     * @return A string message indicating admin board content.
     */
    @GetMapping("/admin") // Map GET requests to "/api/test/admin"
    @PreAuthorize("hasRole('ADMIN')") // Require ADMIN role
    public String adminAccess() {
        return "Admin Board."; // Return a message accessible by admins
    }






}
