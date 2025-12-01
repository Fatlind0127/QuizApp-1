package com.quizapp.quizapp.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quizapp.quizapp.admin.TeacherDashboardService.TestHistoryEntry;
import com.quizapp.quizapp.auth.AuthService;
import com.quizapp.quizapp.domain.ClassEntity;
import com.quizapp.quizapp.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;
    private final TeacherDashboardService teacherDashboardService;

    public AdminController(AuthService authService,
                           TeacherDashboardService teacherDashboardService) {
        this.authService = authService;
        this.teacherDashboardService = teacherDashboardService;
    }

    // --- Teacher Landing ---
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!authService.isAuthorized(session, User.Role.ADMIN)) {
            return "redirect:/?error=unauthorized";
        }

        User currentUser = authService.getCurrentUser(session);
        List<ClassEntity> classes = teacherDashboardService.getClassesForTeacher(currentUser.getId());

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("assignedClasses", classes);
        model.addAttribute("classCount", classes.size());

        return "admin-dashboard";
    }

    // --- My Classes ---
    @GetMapping("/classes")
    public String myClasses(HttpSession session, Model model) {
        if (!authService.isAuthorized(session, User.Role.ADMIN)) {
            return "redirect:/?error=unauthorized";
        }

        User currentUser = authService.getCurrentUser(session);
        List<ClassEntity> classes = teacherDashboardService.getClassesForTeacher(currentUser.getId());

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("classes", classes);

        return "admin-classes";
    }

    // --- Single Class View ---
    @GetMapping("/classes/{classId}")
    public String viewClass(@PathVariable Integer classId,
                            HttpSession session,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (!authService.isAuthorized(session, User.Role.ADMIN)) {
            return "redirect:/?error=unauthorized";
        }

        User currentUser = authService.getCurrentUser(session);

        try {
            ClassEntity classEntity = teacherDashboardService.getClassForTeacher(classId, currentUser.getId());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("classEntity", classEntity);
            model.addAttribute("students", teacherDashboardService.sortStudents(classEntity));
            model.addAttribute("scheduleDays", teacherDashboardService.getScheduleDays(classEntity));
            model.addAttribute("timeRange", teacherDashboardService.formatTimeRange(classEntity));
            return "admin-class-view";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/classes";
        }
    }

    // --- Student Test History ---
    @GetMapping("/classes/{classId}/students/{studentId}/tests")
    public String viewStudentTests(@PathVariable Integer classId,
                                   @PathVariable Integer studentId,
                                   HttpSession session,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (!authService.isAuthorized(session, User.Role.ADMIN)) {
            return "redirect:/?error=unauthorized";
        }

        User currentUser = authService.getCurrentUser(session);

        try {
            ClassEntity classEntity = teacherDashboardService.getClassForTeacher(classId, currentUser.getId());
            User student = teacherDashboardService.findStudentInClass(classEntity, studentId)
                    .orElseThrow(() -> new IllegalArgumentException("Student not found in this class."));

            List<TestHistoryEntry> history = teacherDashboardService.buildTestHistorySnapshot(currentUser);

            model.addAttribute("currentUser", currentUser);
            model.addAttribute("classEntity", classEntity);
            model.addAttribute("student", student);
            model.addAttribute("history", history);

            return "admin-test-history";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/classes";
        }
    }

}

