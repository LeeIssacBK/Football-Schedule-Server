package com.fs.configs;

import com.fs.api.user.domain.User;
import com.fs.api.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationInitializer implements ApplicationRunner {

    private final String USER_ID = "system";
    private final String PASSWORD = "system";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.countByUserId(USER_ID) <= 0) {
            userRepository.save(User.builder()
                    .userId(USER_ID)
                    .password(passwordEncoder.encode(PASSWORD))
                    .name("시스템계정")
                    .roles(List.of("ROLE_ADMIN"))
                    .build());
        }
        log.info("Application startup complete.");
    }

}
