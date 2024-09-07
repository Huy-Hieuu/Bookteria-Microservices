package org.huyhieu.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.entity.IdentityRole;
import org.huyhieu.entity.IdentityUser;
import org.huyhieu.enums.RoleType;
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
                IdentityRole identityRole = new IdentityRole();
                identityRole.setType(RoleType.ADMIN);

                IdentityUser identityUser = new IdentityUser();
                identityUser.setUsername(ADMIN);
                identityUser.addRole(identityRole);
                identityUser.setPassword(passwordEncoder.encode(ADMIN));

                userRepository.save(identityUser);
                log.warn("admin user has been created with default password: admin => please change it");
            }
        };
    }
}
