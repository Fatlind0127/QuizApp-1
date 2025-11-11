package com.quizapp.quizapp.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    
    private final BCryptPasswordEncoder passwordEncoder;
    
    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    /**
     * Hash a plain text password using BCrypt
     * @param plainPassword The plain text password to hash
     * @return The hashed password
     */
    public String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return passwordEncoder.encode(plainPassword);
    }
    
    /**
     * Verify if a plain text password matches a hashed password
     * @param plainPassword The plain text password to verify
     * @param hashedPassword The hashed password to compare against
     * @return true if the passwords match, false otherwise
     */
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        // Check if the stored password is already hashed (BCrypt hashes start with $2a$, $2b$, or $2y$)
        if (hashedPassword.startsWith("$2a$") || hashedPassword.startsWith("$2b$") || hashedPassword.startsWith("$2y$")) {
            return passwordEncoder.matches(plainPassword, hashedPassword);
        }
        // If not hashed (legacy passwords), do plain comparison for backward compatibility during migration
        return plainPassword.equals(hashedPassword);
    }
    
    /**
     * Check if a password is already hashed
     * @param password The password to check
     * @return true if the password appears to be hashed, false otherwise
     */
    public boolean isHashed(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");
    }
}




