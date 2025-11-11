package com.quizapp.quizapp.repository;

import com.quizapp.quizapp.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByQuestionId(Long questionId);
    long countByQuestionIdAndIsCorrectTrue(Long questionId);
}
