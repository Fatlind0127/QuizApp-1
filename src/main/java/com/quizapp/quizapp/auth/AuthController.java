package com.quizapp.quizapp.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quizapp.quizapp.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // --- GET /  → show login page -------------------------
    @GetMapping("/")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                HttpSession session,
                                Model model) {
        // If already logged in, redirect to appropriate dashboard
        User currentUser = authService.getCurrentUser(session);
        if (currentUser != null) {
            return redirectToDashboard(currentUser.getRole());
        }
        
        model.addAttribute("error", error != null);
        return "login";
    }

    // --- POST /  → check credentials and redirect ---------
    @PostMapping("/")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session) {
        User user = authService.authenticate(username, password);

        if (user != null) {
            authService.setCurrentUser(session, user);
            return redirectToDashboard(user.getRole());
        } else {
            return "redirect:/?error=1";
        }
    }

    // --- Logout -------------------------
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        authService.logout(session);
        return "redirect:/?logout=1";
    }

    // --- Helper method to redirect based on role ---------
    private String redirectToDashboard(User.Role role) {
        return switch (role) {
            case SUPERVISOR_ADMIN -> "redirect:/supervisor/dashboard";
            case ADMIN -> "redirect:/admin/dashboard";
            case STUDENT -> "redirect:/student/home";
        };
    }
}
