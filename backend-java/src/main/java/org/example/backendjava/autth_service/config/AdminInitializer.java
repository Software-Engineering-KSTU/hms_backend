package org.example.backendjava.autth_service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backendjava.autth_service.model.entity.Role;
import org.example.backendjava.autth_service.model.entity.User;
import org.example.backendjava.autth_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminUsername = "admin";

        if (userRepository.findByUsername(adminUsername).isEmpty()) {

            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            log.info("Admin has been created");
        }
    }
}
