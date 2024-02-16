package com.fs.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationInitializer implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        log.info("Application startup complete.");
    }


}
