package com.practice.carcompass.dbmigrations;

import com.practice.carcompass.domain.Role;
import com.practice.carcompass.domain.User;
import com.practice.carcompass.repository.UserRepository;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@ChangeUnit(id = "initial-data-setup", order = "001", author = "system")
public class InitialDataSetup {

    @Execution
    public void setupInitialData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        if (userRepository.findByEmail("admin@carcompass.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@carcompass.com");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRoles(List.of(Role.ADMIN, Role.USER));
            userRepository.save(admin);
        }
        
        if (userRepository.findByEmail("user@carcompass.com").isEmpty()) {
            User user = new User();
            user.setEmail("user@carcompass.com");
            user.setPasswordHash(passwordEncoder.encode("user123"));
            user.setRoles(Collections.singletonList(Role.USER));
            userRepository.save(user);
        }
    }

    @RollbackExecution
    public void rollback() {
        // We can leave this empty for now or delete the inserted users
    }
}
