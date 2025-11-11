package com.quizapp.quizapp.domain;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.quizapp.quizapp.user.User;

@Entity
@Table(name = "classes",
       uniqueConstraints = @UniqueConstraint(name = "uk_classes_name", columnNames = "name"))
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // class name, e.g. "11A"
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    // One teacher per class (nullable for now)
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    // Many students per class via join table
    @ManyToMany
    @JoinTable(
        name = "class_students",
        joinColumns = @JoinColumn(name = "class_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> students = new HashSet<>();

    // NEW: "MON,WED,FRI" or similar (keep it simple as text for now)
    @Column(name = "schedule", length = 50)
    private String schedule;

    // NEW: start/end times
    @Column(name = "starting_time")
    private LocalTime startingTime;

    @Column(name = "ending_time")
    private LocalTime endingTime;

    // === Getters / Setters ===
    public Integer getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public User getTeacher() { return teacher; }
    public void setTeacher(User teacher) { this.teacher = teacher; }

    public Set<User> getStudents() { return students; }
    public void setStudents(Set<User> students) { this.students = students; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public LocalTime getStartingTime() { return startingTime; }
    public void setStartingTime(LocalTime startingTime) { this.startingTime = startingTime; }

    public LocalTime getEndingTime() { return endingTime; }
    public void setEndingTime(LocalTime endingTime) { this.endingTime = endingTime; }
}
