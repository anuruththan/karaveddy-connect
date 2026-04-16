package com.example.karaveddy_connect.config;

import com.example.karaveddy_connect.util.JwtAuthenticationFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilterUtil jwtAuthenticationFilterUtil;

    /**
     * Configures stateless security filter chain with role‑based authorization
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/v1/auth/**"
                        ).permitAll()
                        .requestMatchers("/api/v1/dashboard/supper_admin/**").hasAnyRole("SUPPER_ADMIN")
                        .requestMatchers("/api/v1/dashboard/admin/**").hasAnyRole("ADMIN")
                        .requestMatchers("/api/v1/market/**").hasAnyRole("SUPPER_USER")
                        .requestMatchers("/api/v1/post/**").hasAnyRole("SUPPER_ADMIN","ADMIN","COMMON_USER", "PUBLIC")
                        .anyRequest().authenticated()
                );
        http.addFilterAfter(jwtAuthenticationFilterUtil, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}