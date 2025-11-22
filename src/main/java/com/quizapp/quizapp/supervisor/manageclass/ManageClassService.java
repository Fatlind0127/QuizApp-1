package com.quizapp.quizapp.supervisor.manageclass;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quizapp.quizapp.domain.ClassEntity;
import com.quizapp.quizapp.domain.ClassEntityRepository;
import com.quizapp.quizapp.user.User;
import com.quizapp.quizapp.user.UserRepository;

@Service
public class ManageClassService {

    private final UserRepository userRepository;
    private final ClassEntityRepository classRepo;

    public ManageClassService(UserRepository userRepository, ClassEntityRepository classRepo) {
        this.userRepository = userRepository;
        this.classRepo = classRepo;
    }

    @Transactional(readOnly = true)
    public List<User> listStudentsExcludingAdmins(String className) {
        return userRepository.findStudentsInClassExcludingAdmins(className);
    }

    @Transactional(readOnly = true)
    public List<User> listAllTeachers() {
        return userRepository.findAllTeachers();
    }

    @Transactional
    public void ensureTeacherFromUsersIfMissing(String className) {
        Optional<ClassEntity> opt = classRepo.findByName(className);
        if (opt.isEmpty()) return;

        ClassEntity cls = opt.get();
        if (cls.getTeacher() != null) return;

        userRepository.findAnyTeacherForClass(className).ifPresent(t -> {
            cls.setTeacher(t);
            classRepo.save(cls);
        });
    }

    @Transactional
    public void assignTeacher(String className, Integer teacherId) {
        Objects.requireNonNull(teacherId, "Teacher ID cannot be null");
        ClassEntity cls = classRepo.findByName(className)
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found: " + teacherId));
        cls.setTeacher(teacher);
        classRepo.save(cls);
    }

    @Transactional
    public void setSchedule(String className, List<String> days, LocalTime start, LocalTime end) {
        ClassEntity cls = classRepo.findByName(className)
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));

        // Store as "MONDAY,WEDNESDAY" (or whatever the UI submits)
        String schedule = days == null ? null :
                days.stream()
                    .filter(s -> s != null && !s.isBlank())
                    .map(String::trim)
                    .collect(Collectors.joining(","));

        cls.setSchedule(schedule);
        cls.setStartingTime(start);
        cls.setEndingTime(end);
        classRepo.save(cls);
    }

    @Transactional
    public void changeUserClass(Integer userId, String targetClass) {
        userRepository.updateUserClass(userId, targetClass);
    }
}
