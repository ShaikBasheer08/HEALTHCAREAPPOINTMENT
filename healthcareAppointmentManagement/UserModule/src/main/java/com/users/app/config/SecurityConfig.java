 
package com.users.app.config;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.context.annotation.Bean;
 
import org.springframework.context.annotation.Configuration;
 
import org.springframework.security.authentication.AuthenticationManager;
 
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
 
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 
import org.springframework.security.config.http.SessionCreationPolicy;
 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
import org.springframework.security.crypto.password.PasswordEncoder;
 
import org.springframework.security.web.SecurityFilterChain;
 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 
import org.springframework.web.cors.CorsConfiguration;
 
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
 
import org.springframework.web.filter.CorsFilter;
 
import com.users.app.authFilter.AuthFilter;
 
import org.springframework.http.HttpMethod;
 
@Configuration
 
@EnableMethodSecurity
 
public class SecurityConfig {
 
    @Autowired
 
    private AuthFilter authFilter;
 
    @Bean
 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 
        http
 
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
 
            .csrf(csrf -> csrf.disable())
 
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
 
            .authorizeHttpRequests(auth -> auth
 
                .requestMatchers( "/api/users/login").permitAll() // Allow unauthenticated access to login
 
                .requestMatchers( "/api/users/signup","/api/users/**").permitAll() // Allow unauthenticated access to signup (if you have one)
 
                .requestMatchers(HttpMethod.POST, "/appointments/create/**").hasRole("PATIENT")
//                .requestMatchers( "/api/doctor/**").authenticated()
                .requestMatchers("/api/doctor/{id}").permitAll()
                .requestMatchers("/api/doctor/**").hasRole("DOCTOR")
 
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
 
                .anyRequest().authenticated()
 
            )
 
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
 
        return http.build();
 
    }
 
    @Bean
 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
 
        return config.getAuthenticationManager();
 
    }
 
    @Bean
 
    public PasswordEncoder passwordEncoder() {
 
        return new BCryptPasswordEncoder();
 
    }
 
    @Bean
 
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
 
        CorsConfiguration config = new CorsConfiguration();
 
        config.setAllowedOrigins(List.of("http://localhost:3000"));
 
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
 
        config.setAllowedHeaders(List.of("*"));
 
        config.setAllowCredentials(true);
 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
 
        source.registerCorsConfiguration("/**", config);
 
        return source;
 
    }
 
    @Bean
 
    public CorsFilter corsFilter() {
 
        return new CorsFilter(corsConfigurationSource());
 
    }
 
}
 
 
 