package org.huyhieu.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.entity.Role;
import org.huyhieu.entity.User;
import org.huyhieu.enums.RoleEnum;
import org.huyhieu.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
* This config helps to create "admin" (if not exists) when running application for the first time.
* */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
    private static final String ADMIN = "admin";

    private final PasswordEncoder passwordEncoder;

    // This application runner is running when application is started
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (!userRepository.findByUsername(ADMIN).isPresent()) {
                Role role = new Role();
                role.setType(RoleEnum.ADMIN);

                User user = new User();
                user.setUsername(ADMIN);
                user.addRole(role);
                user.setPassword(passwordEncoder.encode(ADMIN));

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin => please change it");
            }
        };
    }
}
