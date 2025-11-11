package com.quizapp.quizapp.auth;

import org.springframework.stereotype.Service;

import com.quizapp.quizapp.user.User;
import com.quizapp.quizapp.user.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public AuthService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && passwordService.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public void setCurrentUser(HttpSession session, User user) {
        session.setAttribute("currentUser", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole().toString());
    }

    public User getCurrentUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            return userRepository.findById(userId).orElse(null);
        }
        return null;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public boolean isAuthorized(HttpSession session, User.Role... allowedRoles) {
        User user = getCurrentUser(session);
        if (user == null) return false;
        for (User.Role role : allowedRoles) {
            if (user.getRole() == role) return true;
        }
        return false;
    }
}
