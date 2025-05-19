package com.users.app.authFilter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.context.annotation.Lazy;
 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 
import org.springframework.security.core.context.SecurityContextHolder;
 
import org.springframework.security.core.userdetails.UserDetails;
 
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
 
import org.springframework.stereotype.Component;
 
import org.springframework.web.filter.OncePerRequestFilter;

import com.users.app.service.JWTService;
 
import com.users.app.service.UserService;

import jakarta.servlet.FilterChain;
 
import jakarta.servlet.ServletException;
 
import jakarta.servlet.http.HttpServletRequest;
 
import jakarta.servlet.http.HttpServletResponse;

@Component
 
public class AuthFilter extends OncePerRequestFilter {
 
    @Autowired
 
    private JWTService jwtService;
 
    @Lazy
 
    @Autowired
 
    UserService userService;

    @Override
 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
 
            throws ServletException, IOException {

        String authHeader = request.getHeader("AUTHORIZATION");
 
        String token = null;
 
        String email = null;
 
        Long userId = null; // Changed userId to Long
 
        String role = null;
 
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
 
            token = authHeader.substring(7);
 
            email = jwtService.extractEmail(token);
 
            try {
 
                userId = Long.parseLong(jwtService.extractUserId(token)); // Extract userId as Long and convert
 
            } catch (NumberFormatException e) {
 
                // Handle the case where userId is not a valid Long (optional, but good practice)
 
                // You might want to log this or throw an exception
 
                e.printStackTrace(); // Log the error
 
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Or another appropriate status
 
                response.getWriter().write("Invalid User ID format in token");
 
                return; // Stop processing the request
 
            }
 
            role = jwtService.extractRole(token);
 
        }
 
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
 
            UserDetails userDetails = userService.loadUserByUsername(email);
 
            if (jwtService.validateToken(token, userDetails)) {
 
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
 
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
 
                SecurityContextHolder.getContext().setAuthentication(authToken);
 
            }
 
        }
 
        request.setAttribute("userId", userId); // Set userId as a Long
 
        request.setAttribute("role", role);
 
        filterChain.doFilter(request, response);
 
    }
 
}
 
 