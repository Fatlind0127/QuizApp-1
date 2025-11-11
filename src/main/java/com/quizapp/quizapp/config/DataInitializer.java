package com.quizapp.quizapp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.quizapp.quizapp.auth.PasswordService;
import com.quizapp.quizapp.user.User;
import com.quizapp.quizapp.user.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public DataInitializer(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create default SupervisorAdmin if not exists
        if (!userRepository.existsByUsername("SupervisorAdmin")) {
            User supervisor = new User();
            supervisor.setUsername("SupervisorAdmin");
            supervisor.setPassword(passwordService.hashPassword("1234")); // Hash password before saving
            supervisor.setRole(User.Role.SUPERVISOR_ADMIN);
            supervisor.setFullName("Supervisor Administrator");
            supervisor.setEmail("supervisor@quizapp.com");
            userRepository.save(supervisor);
            System.out.println("✓ Default SupervisorAdmin account created (Username: SupervisorAdmin, Password: 1234)");
        } else {
            System.out.println("✓ SupervisorAdmin account already exists");
        }
    }
}

