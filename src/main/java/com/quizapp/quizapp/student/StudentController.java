package com.quizapp.quizapp.student;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quizapp.quizapp.auth.AuthService;
import com.quizapp.quizapp.domain.Quiz;
import com.quizapp.quizapp.repository.QuizRepository;
import com.quizapp.quizapp.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final AuthService authService;
    private final QuizRepository quizRepository;

    public StudentController(AuthService authService, QuizRepository quizRepository) {
        this.authService = authService;
        this.quizRepository = quizRepository;
    }

    // --- Home/Dashboard ---
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        if (!authService.isAuthorized(session, User.Role.STUDENT)) {
            return "redirect:/?error=unauthorized";
        }

        User currentUser = authService.getCurrentUser(session);
        List<Quiz> quizzes = quizRepository.findAll();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("quizzes", quizzes);

        return "student-home";
    }

    // --- View Available Quizzes ---
    // @GetMapping("/quizzes")
    // public String viewQuizzes(HttpSession session, Model model) {
    //     if (!authService.isAuthorized(session, User.Role.STUDENT)) {
    //         return "redirect:/?error=unauthorized";
    //     }

    //     List<Quiz> quizzes = quizRepository.findAll();
    //     model.addAttribute("quizzes", quizzes);
    //     model.addAttribute("currentUser", authService.getCurrentUser(session));

    //     return "student-quizzes";
    // }
}

