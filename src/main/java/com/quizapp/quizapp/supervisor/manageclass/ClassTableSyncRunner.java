package com.quizapp.quizapp.supervisor.manageclass;

import com.quizapp.quizapp.domain.ClassEntity;
import com.quizapp.quizapp.domain.ClassEntityRepository;
import com.quizapp.quizapp.user.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ClassTableSyncRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final ClassEntityRepository classRepo;

    public ClassTableSyncRunner(UserRepository userRepository,
                                ClassEntityRepository classRepo) {
        this.userRepository = userRepository;
        this.classRepo = classRepo;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // Pull distinct class names from users.class_name
        List<String> classNames = userRepository.findDistinctClassNames();
        if (classNames == null || classNames.isEmpty()) {
            System.out.println("[ClassSync] No class names found in users.");
            return;
        }

        int inserted = 0;
        for (String cn : classNames) {
            if (cn == null || cn.isBlank()) continue;
            if (!classRepo.existsByName(cn)) {
                ClassEntity c = new ClassEntity();
                c.setName(cn);
                classRepo.save(c);
                inserted++;
            }
        }
        System.out.println("[ClassSync] Classes ensured from users: +" + inserted + " new rows.");
    }
}
