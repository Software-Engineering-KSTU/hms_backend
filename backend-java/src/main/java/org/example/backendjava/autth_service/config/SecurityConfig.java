package org.example.backendjava.autth_service.config;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.autth_service.model.entity.Role;
import org.example.backendjava.autth_service.service.UserDetailsServiceImpl;
import org.example.backendjava.autth_service.util.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация безопасности приложения.
 * <p>
 * Настраивает JWT-аутентификацию, авторизацию по ролям,
 * обработку выхода из системы и политику сессий.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/auth/patient-register","/api/auth/login", "/swagger-ui/index.html", "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html", "/docs/openapi.yml").permitAll()
                        .requestMatchers("/api/cards/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/cards/blocking/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/auth/doctor-register").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/cards/blocking/request").authenticated()
                        .requestMatchers("/api/transactions/**").authenticated()
                        .requestMatchers("/api/cards/my/**").authenticated()
                        .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers("api/appointments/**").authenticated()
                        .anyRequest().permitAll()
                )
                .userDetailsService(userDetailsService)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}