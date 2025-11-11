package com.quizapp.quizapp.supervisor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

import com.quizapp.quizapp.auth.AuthService;
import com.quizapp.quizapp.user.User;

@Controller
@RequestMapping("/supervisor/create-class")
public class CreateClassController {

    private final AuthService authService;

    public CreateClassController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String createClassPage(HttpSession session) {
        if (!authService.isAuthorized(session, User.Role.SUPERVISOR_ADMIN)) {
            return "redirect:/?error=unauthorized";
        }
        return "supervisor/create-class";
    }
}
