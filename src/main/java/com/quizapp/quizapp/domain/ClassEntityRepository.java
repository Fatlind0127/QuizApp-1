package com.quizapp.quizapp.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassEntityRepository extends JpaRepository<ClassEntity, Integer> {
    Optional<ClassEntity> findByName(String name);
    boolean existsByName(String name);

    List<ClassEntity> findAllByTeacher_Id(Integer teacherId);
    Optional<ClassEntity> findByIdAndTeacher_Id(Integer id, Integer teacherId);
}
