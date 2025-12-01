package com.quizapp.quizapp.admin;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quizapp.quizapp.domain.ClassEntity;
import com.quizapp.quizapp.domain.ClassEntityRepository;
import com.quizapp.quizapp.domain.Quiz;
import com.quizapp.quizapp.repository.QuizRepository;
import com.quizapp.quizapp.user.User;
import com.quizapp.quizapp.user.UserRepository;

@Service
public class TeacherDashboardService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final int[] SCORE_PRESETS = { 80, 60, 35, 92 };

    private final ClassEntityRepository classRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public TeacherDashboardService(ClassEntityRepository classRepository,
                                   QuizRepository quizRepository,
                                   UserRepository userRepository) {
        this.classRepository = classRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<ClassEntity> getClassesForTeacher(Integer teacherId) {
        if (teacherId == null) {
            return List.of();
        }
        return classRepository.findAllByTeacher_Id(teacherId)
                .stream()
                .sorted(Comparator.comparing(
                        ClassEntity::getName,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .toList();
    }

    @Transactional(readOnly = true)
    public ClassEntity getClassForTeacher(Integer classId, Integer teacherId) {
        if (classId == null || teacherId == null) {
            throw new IllegalArgumentException("Class or teacher could not be resolved.");
        }

        ClassEntity classEntity = classRepository.findByIdAndTeacher_Id(classId, teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found or not assigned to you."));

        // Initialize lazy collection while transaction is open
        classEntity.getStudents().size();
        return classEntity;
    }

    public List<String> getScheduleDays(ClassEntity classEntity) {
        if (classEntity == null || classEntity.getSchedule() == null || classEntity.getSchedule().isBlank()) {
            return List.of();
        }

        return Arrays.stream(classEntity.getSchedule().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    public String formatTimeRange(ClassEntity classEntity) {
        if (classEntity == null || classEntity.getStartingTime() == null || classEntity.getEndingTime() == null) {
            return "Schedule TBD";
        }
        return classEntity.getStartingTime().format(TIME_FORMATTER) + " - " +
               classEntity.getEndingTime().format(TIME_FORMATTER);
    }

    public List<User> sortStudents(ClassEntity classEntity) {
        if (classEntity == null || classEntity.getName() == null) {
            return List.of();
        }

        List<User> students = userRepository.findStudentsInClassExcludingAdmins(classEntity.getName());

        return students.stream()
                .sorted(Comparator.comparing(user -> Optional.ofNullable(user.getFullName())
                        .orElse(user.getUsername())))
                .toList();
    }

    public Optional<User> findStudentInClass(ClassEntity classEntity, Integer studentId) {
        if (classEntity == null || classEntity.getName() == null || studentId == null) {
            return Optional.empty();
        }

        return userRepository.findStudentsInClassExcludingAdmins(classEntity.getName())
                .stream()
                .filter(student -> studentId.equals(student.getId()))
                .findFirst();
    }

    @Transactional(readOnly = true)
    public List<TestHistoryEntry> buildTestHistorySnapshot(User teacher) {
        List<Quiz> quizzes = quizRepository.findAll();

        if (quizzes.isEmpty()) {
            return defaultHistory(teacher);
        }

        AtomicInteger counter = new AtomicInteger();
        String instructor = teacher != null && teacher.getFullName() != null
                ? teacher.getFullName()
                : "Instructor";

        return quizzes.stream()
                .map(quiz -> {
                    int presetIndex = counter.getAndIncrement() % SCORE_PRESETS.length;
                    int score = SCORE_PRESETS[presetIndex];
                    String title = quiz.getTitle() != null ? quiz.getTitle() : "Quiz";
                    return new TestHistoryEntry(title.toUpperCase(), score, 100, instructor);
                })
                .toList();
    }

    private List<TestHistoryEntry> defaultHistory(User teacher) {
        String instructor = teacher != null && teacher.getFullName() != null
                ? teacher.getFullName()
                : "Instructor";

        List<TestHistoryEntry> fallback = new ArrayList<>();
        fallback.add(new TestHistoryEntry("JAVA", 80, 100, instructor));
        fallback.add(new TestHistoryEntry("REACT JS", 60, 100, instructor));
        fallback.add(new TestHistoryEntry("PYTHON", 35, 100, instructor));
        return fallback;
    }

    public record TestHistoryEntry(String subject, int score, int total, String instructor) { }
}

