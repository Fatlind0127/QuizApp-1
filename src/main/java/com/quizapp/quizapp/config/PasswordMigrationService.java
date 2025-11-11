package com.quizapp.quizapp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.quizapp.quizapp.auth.PasswordService;
import com.quizapp.quizapp.user.User;
import com.quizapp.quizapp.user.UserRepository;

import java.util.List;

/**
 * Migration service to hash existing plain text passwords in the database.
 * This runs after DataInitializer to ensure all existing passwords are hashed.
 */
@Component
@Order(2) // Run after DataInitializer (which has default order 0)
public class PasswordMigrationService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public PasswordMigrationService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<User> allUsers = userRepository.findAll();
        int migratedCount = 0;
        
        for (User user : allUsers) {
            String currentPassword = user.getPassword();
            
            // Check if password is already hashed
            if (currentPassword != null && !passwordService.isHashed(currentPassword)) {
                // Hash the plain text password
                String hashedPassword = passwordService.hashPassword(currentPassword);
                user.setPassword(hashedPassword);
                userRepository.save(user);
                migratedCount++;
                System.out.println("✓ Migrated password for user: " + user.getUsername());
            }
        }
        
        if (migratedCount > 0) {
            System.out.println("✓ Password migration completed: " + migratedCount + " user(s) migrated");
        } else {
            System.out.println("✓ All passwords are already hashed");
        }
    }
}




