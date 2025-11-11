package com.quizapp.quizapp.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    List<User> findByRole(User.Role role);
    boolean existsByUsername(String username);

    @Query(value = """
            SELECT DISTINCT class_name
            FROM users
            WHERE class_name IS NOT NULL AND class_name <> ''
            ORDER BY class_name ASC
            """, nativeQuery = true)
    List<String> findDistinctClassNames();

    // Students in a class, EXCLUDING admins
    @Query(value = """
            SELECT *
            FROM users
            WHERE class_name = :className
              AND role <> 'ADMIN'
            ORDER BY full_name ASC
            """, nativeQuery = true)
    List<User> findStudentsInClassExcludingAdmins(@Param("className") String className);

    // All teachers (role ADMIN)
    @Query(value = """
            SELECT *
            FROM users
            WHERE role = 'ADMIN'
            ORDER BY full_name ASC
            """, nativeQuery = true)
    List<User> findAllTeachers();

    // If any teacher has class_name = this class, pick one (for auto-mapping)
    @Query(value = """
            SELECT *
            FROM users
            WHERE role = 'ADMIN' AND class_name = :className
            ORDER BY id ASC
            LIMIT 1
            """, nativeQuery = true)
    Optional<User> findAnyTeacherForClass(@Param("className") String className);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE users SET class_name = :newClass WHERE id = :userId", nativeQuery = true)
    int updateUserClass(@Param("userId") Integer userId, @Param("newClass") String newClass);
}
