package com.quizapp.quizapp.repository;

import com.quizapp.quizapp.domain.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Load a quiz with its questions and options in one go (avoids Lazy problems)
    @EntityGraph(attributePaths = {"questions", "questions.options"})
    Quiz findWithQuestionsById(Long id);
}
