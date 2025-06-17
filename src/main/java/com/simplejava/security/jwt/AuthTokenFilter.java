package com.simplejava.security.jwt;

import com.simplejava.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 6/9/2025
 * Time: 11:28 PM
 */
public class AuthTokenFilter  extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsServiceImpl userDetailsService;

    public AuthTokenFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Parse and validate the JWT token from the request
            String jwt = parseJwt(request);
            if (jwt != null && jwtService.validateJwtToken(jwt)) {
                // Get the username from the validated JWT token
                String username = jwtService.getUserNameFromJwtToken(jwt);

                // Load user details from the username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Create an authentication token with the user details
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

                // Set additional details from the request
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Log any errors that occur during authentication
            logger.error("Cannot set user authentication: {}", e);
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
    /**
     * Parses the JWT token from the Authorization header of the request.
     *
     * @param request The HTTP request containing the Authorization header.
     * @return The JWT token if present and valid, otherwise null.
     */
    private String parseJwt(HttpServletRequest request) {
        // Get the Authorization header from the request
        String headerAuth = request.getHeader("Authorization");

        // Check if the header is valid and starts with "Bearer "
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            // Extract the JWT token from the header
            return headerAuth.substring(7);
        }

        return null; // Return null if no valid token is found
    }



}
