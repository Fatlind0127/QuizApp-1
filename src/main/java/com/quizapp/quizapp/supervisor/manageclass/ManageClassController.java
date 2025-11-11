package com.quizapp.quizapp.supervisor.manageclass;

import com.quizapp.quizapp.domain.ClassEntity;
import com.quizapp.quizapp.domain.ClassEntityRepository;
import com.quizapp.quizapp.user.User;
import com.quizapp.quizapp.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ManageClassController {

    private final UserRepository userRepository;
    private final ClassEntityRepository classRepo;
    private final ManageClassService manageClassService;

    public ManageClassController(UserRepository userRepository,
                                 ClassEntityRepository classRepo,
                                 ManageClassService manageClassService) {
        this.userRepository = userRepository;
        this.classRepo = classRepo;
        this.manageClassService = manageClassService;
        System.out.println("ManageClassController initialized");
    }

    // === LIST CLASSES PAGE ===
    // URL: http://localhost:8080/supervisor/manage-classes
    @GetMapping("/supervisor/manage-classes")
    public String manageClasses(Model model) {
        model.addAttribute("classNames", userRepository.findDistinctClassNames());
        return "supervisor/manage-classes";
    }

    // === VIEW A SINGLE CLASS ===
    // URL: /manage-class/view-class.html?name=11A
    @GetMapping("/manage-class/view-class.html")
    public String viewClass(@RequestParam("name") String className,
                            Model model,
                            @ModelAttribute("msg") String msg) {

        // Auto-map teacher from users if class has none but an ADMIN with this class_name exists
        manageClassService.ensureTeacherFromUsersIfMissing(className);

        Optional<ClassEntity> classOpt = classRepo.findByName(className);
        ClassEntity classEntity = classOpt.orElse(null);

        // Students list excludes ADMINs
        List<User> students = manageClassService.listStudentsExcludingAdmins(className);

        // Teachers for dropdown (Assign/Change Teacher modal)
        List<User> teachers = manageClassService.listAllTeachers();

        model.addAttribute("className", className);
        model.addAttribute("students", students);
        model.addAttribute("teachers", teachers);
        model.addAttribute("classEntity", classEntity);

        boolean hasTeacher = classEntity != null && classEntity.getTeacher() != null;
        boolean hasSchedule = classEntity != null
                && classEntity.getSchedule() != null && !classEntity.getSchedule().isBlank()
                && classEntity.getStartingTime() != null && classEntity.getEndingTime() != null;

        model.addAttribute("hasTeacher", hasTeacher);
        model.addAttribute("hasSchedule", hasSchedule);

        // For the "Change class" modal on each student
        model.addAttribute("classNames", userRepository.findDistinctClassNames());

        if (msg != null && !msg.isBlank()) model.addAttribute("msg", msg);

        return "supervisor/view-class";
    }

    // === MOVE A STUDENT TO ANOTHER CLASS ===
    // Called by the "Change class" modal in view-class.html
    @PostMapping("/manage-class/change-class")
    public String changeClass(@RequestParam("userId") Integer userId,
                              @RequestParam("targetClass") String targetClass,
                              @RequestParam("fromClass") String fromClass,
                              RedirectAttributes ra) {
        if (targetClass == null || targetClass.isBlank()) {
            ra.addFlashAttribute("msg", "Please choose a class.");
        } else {
            manageClassService.changeUserClass(userId, targetClass);
            ra.addFlashAttribute("msg", "Student moved to class " + targetClass + ".");
        }
        return "redirect:/manage-class/view-class.html?name=" +
                URLEncoder.encode(fromClass, StandardCharsets.UTF_8);
    }

    // === ASSIGN / CHANGE TEACHER ===
    @PostMapping("/manage-class/assign-teacher")
    public String assignTeacher(@RequestParam("className") String className,
                                @RequestParam("teacherId") Integer teacherId,
                                RedirectAttributes ra) {
        manageClassService.assignTeacher(className, teacherId);
        ra.addFlashAttribute("msg", "Teacher assigned.");
        return "redirect:/manage-class/view-class.html?name=" +
                URLEncoder.encode(className, StandardCharsets.UTF_8);
    }

    // === SET / EDIT SCHEDULE ===
    @PostMapping("/manage-class/set-schedule")
    public String setSchedule(@RequestParam("className") String className,
                              @RequestParam(value = "days", required = false) String[] days,
                              @RequestParam("startingTime") String startingTime,
                              @RequestParam("endingTime") String endingTime,
                              RedirectAttributes ra) {

        List<String> dayList = (days == null) ? List.of() : Arrays.asList(days);

        manageClassService.setSchedule(
                className,
                dayList,
                LocalTime.parse(startingTime), // expects HH:mm (24h)
                LocalTime.parse(endingTime)
        );

        ra.addFlashAttribute("msg", "Schedule saved.");
        return "redirect:/manage-class/view-class.html?name=" +
                URLEncoder.encode(className, StandardCharsets.UTF_8);
    }
}
