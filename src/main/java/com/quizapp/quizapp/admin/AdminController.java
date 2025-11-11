package com.quizapp.quizapp.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quizapp.quizapp.auth.AuthService;
import com.quizapp.quizapp.domain.Quiz;
import com.quizapp.quizapp.repository.QuizRepository;
import com.quizapp.quizapp.user.User;
import com.quizapp.quizapp.user.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    public AdminController(AuthService authService, UserRepository userRepository, 
                          QuizRepository quizRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    // --- Dashboard ---
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!authService.isAuthorized(session, User.Role.ADMIN)) {
            return "redirect:/?error=unauthorized";
        }

        User currentUser = authService.getCurrentUser(session);
        List<User> students = userRepository.findByRole(User.Role.STUDENT);
        List<Quiz> quizzes = quizRepository.findAll();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("students", students);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("totalStudents", students.size());
        model.addAttribute("totalQuizzes", quizzes.size());

        return "admin-dashboard";
    }

    // --- Create Student ---
    // @PostMapping("/create-student")
    // public String createStudent(@RequestParam String username,
    //                            @RequestParam String password,
    //                            @RequestParam(required = false) String fullName,
    //                            @RequestParam(required = false) String email,
    //                            HttpSession session) {
    //     if (!authService.isAuthorized(session, User.Role.ADMIN)) {
    //         return "redirect:/?error=unauthorized";
    //     }

    //     if (userRepository.existsByUsername(username)) {
    //         return "redirect:/admin/dashboard?error=username_exists";
    //     }

    //     User student = new User();
    //     student.setUsername(username);
    //     student.setPassword(password);
    //     student.setRole(User.Role.STUDENT);
    //     student.setFullName(fullName);
    //     student.setEmail(email);
    //     userRepository.save(student);

    //     return "redirect:/admin/dashboard?success=student_created";
    // }

    // --- Delete Student ---
    // @PostMapping("/delete-student/{id}")
    // public String deleteStudent(@PathVariable Integer id, HttpSession session) {
    //     if (!authService.isAuthorized(session, User.Role.ADMIN)) {
    //         return "redirect:/?error=unauthorized";
    //     }

    //     User student = userRepository.findById(id).orElse(null);
    //     if (student != null && student.getRole() == User.Role.STUDENT) {
    //         userRepository.deleteById(id);
    //         return "redirect:/admin/dashboard?success=student_deleted";
    //     }

    //     return "redirect:/admin/dashboard?error=cannot_delete";
    // }

    // --- View Students ---
    // @GetMapping("/students")
    // public String viewStudents(HttpSession session, Model model) {
    //     if (!authService.isAuthorized(session, User.Role.ADMIN)) {
    //         return "redirect:/?error=unauthorized";
    //     }

    //     List<User> students = userRepository.findByRole(User.Role.STUDENT);
    //     model.addAttribute("students", students);
    //     model.addAttribute("currentUser", authService.getCurrentUser(session));

    //     return "admin-students";
    // }
}

