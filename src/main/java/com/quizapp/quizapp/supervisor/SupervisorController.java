package com.quizapp.quizapp.supervisor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapp.quizapp.auth.AuthService;
import com.quizapp.quizapp.auth.PasswordService;
import com.quizapp.quizapp.domain.Quiz;
import com.quizapp.quizapp.repository.QuizRepository;
import com.quizapp.quizapp.user.User;
import com.quizapp.quizapp.user.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/supervisor")
public class SupervisorController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final PasswordService passwordService;
    

    private static int idCounter = 1;

    public SupervisorController(AuthService authService, UserRepository userRepository,
                                QuizRepository quizRepository, PasswordService passwordService) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.passwordService = passwordService;
        
        System.out.println("SupervisorController initialized");
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!authService.isAuthorized(session, User.Role.SUPERVISOR_ADMIN)) {
            return "redirect:/?error=unauthorized";
        }

        User currentUser = authService.getCurrentUser(session);
        List<User> admins = userRepository.findByRole(User.Role.ADMIN);
        List<User> students = userRepository.findByRole(User.Role.STUDENT);
        List<Quiz> quizzes = quizRepository.findAll();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("admins", admins);
        model.addAttribute("students", students);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("totalAdmins", admins.size());
        model.addAttribute("totalStudents", students.size());
        model.addAttribute("totalQuizzes", quizzes.size());

        return "supervisor-dashboard";
    }


    @GetMapping("/add-student")
    public String addStudentPage(HttpSession session) {
        if (!authService.isAuthorized(session, User.Role.SUPERVISOR_ADMIN)) {
            return "redirect:/?error=unauthorized";
        }
        return "supervisor/add-student";
    }

    @PostMapping("/add-student")
    public String addStudentsToJsonAndDB(
            @RequestParam("className") String className,
            @RequestParam("name") List<String> names,
            @RequestParam("surname") List<String> surnames,
            Model model
    ) throws IOException {

        System.out.println("POST /add-student called");
        System.out.println("Class: " + className);
        System.out.println("Names: " + names);
        System.out.println("Surnames: " + surnames);

        if (names.size() != surnames.size()) {
            model.addAttribute("error", "Names and surnames count do not match.");
            return "supervisor/add-student";
        }

        List<Map<String, Object>> studentsJsonList = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            String firstName = names.get(i).trim();
            String lastName = surnames.get(i).trim();
            if (firstName.isEmpty() || lastName.isEmpty()) continue;

            // Username: firstInitial.lastname (e.g., "f.hamza" for Fatlind Hamza)
            String username = (firstName.substring(0, 1).toLowerCase() + "." + lastName.toLowerCase()).replaceAll("\\s+", "");
            username = ensureUniqueUsername(username);
            
            // Password: lastNameInitial.firstname (e.g., "h.fatlind" for Fatlind Hamza)
            String lastNameFirstInitial = lastName.substring(0, 1).toLowerCase();
            String password = (lastNameFirstInitial + "." + firstName.toLowerCase()).replaceAll("\\s+", "");

            System.out.println("Creating student: " + firstName + " " + lastName);
            System.out.println("Username: " + username + ", Password: " + password);

            User student = new User();
            student.setFullName(firstName + " " + lastName);
            student.setUsername(username);
            student.setPassword(passwordService.hashPassword(password)); // Hash password before saving
            student.setRole(User.Role.STUDENT);
            student.setEmail(null);
            student.setClassName(className);
            
            try {
                User saved = userRepository.save(student);
                System.out.println("Saved student with ID: " + saved.getId());
            } catch (Exception e) {
                System.err.println("Error saving student: " + e.getMessage());
            }



            Map<String, Object> studentJson = new HashMap<>();
            studentJson.put("ID", idCounter++);
            studentJson.put("Username", username);
            studentJson.put("Password", password);
            studentJson.put("Class", className);

            studentsJsonList.add(studentJson);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new FileWriter("students.json"), studentsJsonList);

        model.addAttribute("message", "Students saved to database and students.json!");
        model.addAttribute("students", studentsJsonList);

        return "supervisor/add-student-result";
    }

    @GetMapping("/assign-users")
    public String assignUsersPage(HttpSession session) {
        if (!authService.isAuthorized(session, User.Role.SUPERVISOR_ADMIN)) {
            return "redirect:/?error=unauthorized";
        }
        return "supervisor/assign-users";
    }



    @GetMapping("/manage-users")
    public String manageUsersPage(
            HttpSession session,
            Model model,
            @RequestParam(required = false) String filterClass,
            @RequestParam(required = false) String filterRole) {
        if (!authService.isAuthorized(session, User.Role.SUPERVISOR_ADMIN)) {
            return "redirect:/?error=unauthorized";
        }

        // Get all users
        List<User> allUsers = userRepository.findAll();
        
        // Apply filters
        List<User> filteredUsers = allUsers;
        if (filterClass != null && !filterClass.isEmpty() && !filterClass.equals("all")) {
            filteredUsers = filteredUsers.stream()
                    .filter(u -> filterClass.equals(u.getClassName()))
                    .toList();
        }
        if (filterRole != null && !filterRole.isEmpty() && !filterRole.equals("all")) {
            User.Role roleFilter = User.Role.valueOf(filterRole);
            filteredUsers = filteredUsers.stream()
                    .filter(u -> u.getRole() == roleFilter)
                    .toList();
        }

        // Get distinct class names for filter dropdown
        List<String> classNames = userRepository.findDistinctClassNames();
        
        model.addAttribute("users", filteredUsers);
        model.addAttribute("classNames", classNames);
        model.addAttribute("roles", User.Role.values());
        model.addAttribute("filterClass", filterClass != null ? filterClass : "all");
        model.addAttribute("filterRole", filterRole != null ? filterRole : "all");
        
        return "supervisor/manage-users";
    }

    @PostMapping("/manage-users/edit")
    public String editUser(
            HttpSession session,
            @RequestParam Integer userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String role,
            RedirectAttributes redirectAttributes) {
        if (!authService.isAuthorized(session, User.Role.SUPERVISOR_ADMIN)) {
            return "redirect:/?error=unauthorized";
        }

        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "User ID is required");
            return "redirect:/supervisor/manage-users";
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/supervisor/manage-users";
        }

        // Update user fields if provided
        if (username != null && !username.trim().isEmpty()) {
            // Check if username is already taken by another user
            if (!username.equals(user.getUsername()) && userRepository.existsByUsername(username)) {
                redirectAttributes.addFlashAttribute("error", "Username already exists");
                return "redirect:/supervisor/manage-users";
            }
            user.setUsername(username.trim());
        }
        if (fullName != null) {
            user.setFullName(fullName.trim().isEmpty() ? null : fullName.trim());
        }
        if (email != null) {
            user.setEmail(email.trim().isEmpty() ? null : email.trim());
        }
        if (className != null) {
            user.setClassName(className.trim().isEmpty() ? null : className.trim());
        }
        if (role != null && !role.isEmpty()) {
            try {
                user.setRole(User.Role.valueOf(role));
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("error", "Invalid role");
                return "redirect:/supervisor/manage-users";
            }
        }

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "User updated successfully");
        return "redirect:/supervisor/manage-users";
    }

    @PostMapping("/manage-users/remove/{id}")
    public String removeUser(
            HttpSession session,
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes) {
        if (!authService.isAuthorized(session, User.Role.SUPERVISOR_ADMIN)) {
            return "redirect:/?error=unauthorized";
        }

        if (id == null) {
            redirectAttributes.addFlashAttribute("error", "User ID is required");
            return "redirect:/supervisor/manage-users";
        }

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/supervisor/manage-users";
        }

        // Prevent deleting the current logged-in user
        User currentUser = authService.getCurrentUser(session);
        if (currentUser != null && currentUser.getId().equals(id)) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete your own account");
            return "redirect:/supervisor/manage-users";
        }

        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "User removed successfully");
        return "redirect:/supervisor/manage-users";
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