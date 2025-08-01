package com.imsunnnyyy.blog.config;

import com.imsunnnyyy.blog.domain.entities.User;
import com.imsunnnyyy.blog.repositories.UserRepository;
import com.imsunnnyyy.blog.security.BlogUserDetailsService;
import com.imsunnnyyy.blog.security.JwtAuthenticationFilter;
import com.imsunnnyyy.blog.services.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
        return new JwtAuthenticationFilter(authenticationService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        BlogUserDetailsService blogUserDetailsService = new BlogUserDetailsService(userRepository);

        String email = "user2@test.com";

        // Avoid duplicate creation on every startup
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            User newUser = User.builder()
                    .name("Test User2")
                    .email(email)
                    .password(passwordEncoder().encode("Test@123"))
                    .build();
            userRepository.save(newUser);
        }

        return blogUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public login
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()

                        // Public GET access
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()

                        // Authenticated for non-GET endpoints
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/v1/posts/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/posts/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/posts/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/v1/tags/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/tags/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tags/**").authenticated()

                        // Authenticated for drafts
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/drafts").authenticated()

                        // Everything else
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
