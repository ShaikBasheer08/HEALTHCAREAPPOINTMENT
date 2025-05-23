package com.healthcare.appointment.security;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Request to: " + request.getRequestURI()); // Log the requested URI

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Authorization header missing or invalid.");
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("Received JWT Token: " + token);

        if (!jwtUtil.validateToken(token)) {
            System.out.println("Invalid JWT Token.");
            chain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.extractEmail(token);
        String roles = jwtUtil.extractRoles(token);
        System.out.println("Extracted username: " + username + ", roles: " + roles);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(
            roles.startsWith("ROLE_") ? roles : "ROLE_" + roles
        ));
        System.out.println("Granted Authorities: " + authorities);

        UserDetails userDetails = new User(username, "N/A", authorities);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        System.out.println("Security Context Authentication set: " + SecurityContextHolder.getContext().getAuthentication());

        request.setAttribute("jwtToken", token);
        chain.doFilter(request, response);
    }
}