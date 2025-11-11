package com.quizapp.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.quizapp.quizapp.domain.*;
import com.quizapp.quizapp.repository.*;

@SpringBootApplication
public class QuizappApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizappApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(QuizRepository quizRepo) {
        return args -> {
            try {
                if (quizRepo.count() > 0) return; // seed only once

                Quiz quiz = new Quiz();
                quiz.setTitle("General Knowledge");
                quiz.setDescription("Demo quiz for testing");

                Question q1 = new Question();
                q1.setText("Capital of France?");
                Option o11 = new Option(); o11.setText("Paris");  o11.setCorrect(true);
                Option o12 = new Option(); o12.setText("Berlin"); o12.setCorrect(false);
                q1.addOption(o11); q1.addOption(o12);

                Question q2 = new Question();
                q2.setText("2 + 2 = ?");
                Option o21 = new Option(); o21.setText("3"); o21.setCorrect(false);
                Option o22 = new Option(); o22.setText("4"); o22.setCorrect(true);
                q2.addOption(o21); q2.addOption(o22);

                quiz.addQuestion(q1);
                quiz.addQuestion(q2);

                quizRepo.save(quiz);
                System.out.println("✅ Seeded 1 quiz with 2 questions.");
            } catch (Exception e) {
                e.printStackTrace(); // don’t kill startup if something goes wrong
            }
        };
    }
}
