package com.fs.common.utils;

import com.fs.common.exceptions.NotFoundException;
import com.fs.configs.security.domain.Code;
import com.fs.configs.security.domain.CodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CodeProvider {

    private final CodeRepository codeRepository;

    public static Code apiKey;
    public static Code apiHost;

    @PostConstruct
    void initCodes() {
        apiKey = codeRepository.findByK("X-RapidAPI-Key").orElseThrow(() -> new NotFoundException("apiKey"));
        apiHost = codeRepository.findByK("X-RapidAPI-Host").orElseThrow(() -> new NotFoundException("apiHost"));
    }

}
