package com.quizapp.quizapp.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @OneToMany(
        mappedBy = "quiz",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Question> questions = new ArrayList<>();

    // Helpers (optional but handy)
    public void addQuestion(Question q) {
        questions.add(q);
        q.setQuiz(this);
    }

    public void removeQuestion(Question q) {
        questions.remove(q);
        q.setQuiz(null);
    }

    // getters/setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
