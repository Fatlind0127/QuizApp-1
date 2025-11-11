package com.quizapp.quizapp.supervisor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.quizapp.quizapp.auth.AuthService;
import com.quizapp.quizapp.auth.PasswordService;
import com.quizapp.quizapp.user.User;
import com.quizapp.quizapp.user.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/supervisor")
public class AddTeacherController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public AddTeacherController(AuthService authService, UserRepository userRepository, PasswordService passwordService) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    // Show form
    @GetMapping("/add-teacher")
    public String addTeacherForm(HttpSession session,
                                 @RequestParam(value = "error", required = false) String error,
                                 @RequestParam(value = "success", required = false) String success,
                                 Model model) {
        if (!authService.isAuthorized(session, User.Role.SUPERVISOR_ADMIN)) {
            return "redirect:/?error=unauthorized";
        }
        model.addAttribute("error", error);
        model.addAttribute("success", success);
        return "supervisor/add-teacher"; // templates/supervisor/add-teacher.html
    }

    // Handle submit
    @PostMapping("/add-teacher")
    public String addTeacherSubmit(HttpSession session,
                                   @RequestParam("fullName") String fullName,
                                   @RequestParam(value = "email", required = false) String email,
                                   @RequestParam(value = "password", required = false) String password,
                                   @RequestParam(value = "confirmPassword", required = false) String confirmPassword) {
        if (!authService.isAuthorized(session, User.Role.SUPERVISOR_ADMIN)) {
            return "redirect:/?error=unauthorized";
        }

        String trimmedName = fullName == null ? "" : fullName.trim();
        if (trimmedName.isEmpty()) {
            return "redirect:/supervisor/add-teacher?error=full_name_required";
        }

        // Password logic
        String finalPassword;
        boolean pwdEmpty = (password == null || password.isBlank());
        boolean cpEmpty  = (confirmPassword == null || confirmPassword.isBlank());
        if (pwdEmpty && cpEmpty) {
            finalPassword = buildDefaultPassword(trimmedName);  // E.ramani
        } else {
            if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
                return "redirect:/supervisor/add-teacher?error=passwords_do_not_match";
            }
            finalPassword = password;
        }

        // Username logic
        String username = buildUsername(email, trimmedName);
        username = ensureUniqueUsername(username);

        // Create user
        User teacher = new User();
        teacher.setFullName(trimmedName);
        teacher.setEmail(email == null ? null : email.trim());
        teacher.setUsername(username);
        teacher.setPassword(passwordService.hashPassword(finalPassword)); // Hash password before saving
        teacher.setRole(User.Role.ADMIN);

        userRepository.save(teacher);

        return "redirect:/supervisor/add-teacher?success=created";
    }

    // ---------- helpers ----------

    private String buildDefaultPassword(String fullName) {
        String[] parts = fullName.trim().split("\\s+");
        String first = parts[0];
        String last  = parts.length > 1 ? parts[parts.length - 1] : "";
        String firstLetter = first.isEmpty() ? "" : first.substring(0, 1);
        return last.isEmpty() ? (firstLetter + ".").trim() : firstLetter + "." + last.toLowerCase();
    }

    private String buildUsername(String email, String fullName) {
        if (email != null && !email.isBlank() && email.contains("@")) {
            return email.substring(0, email.indexOf('@')).toLowerCase();
        }
        String[] parts = fullName.trim().split("\\s+");
        String first = parts[0];
        String last  = parts.length > 1 ? parts[parts.length - 1] : "";
        String firstInitial = first.isEmpty() ? "" : first.substring(0, 1).toLowerCase();
        String base = (firstInitial + last).toLowerCase().replaceAll("[^a-z0-9]", "");
        return base.isEmpty() ? "teacher" : base;
    }

    private String ensureUniqueUsername(String base) {
        String candidate = base;
        int n = 1;
        while (userRepository.existsByUsername(candidate)) {
            candidate = base + n;
            n++;
        }
        return candidate;
    }
}
