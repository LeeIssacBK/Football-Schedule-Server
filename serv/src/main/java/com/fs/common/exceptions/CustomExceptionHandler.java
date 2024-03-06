package com.fs.common.exceptions;

import com.fs.api.log.domain.LogError;
import com.fs.api.log.domain.LogErrorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CustomExceptionHandler {

    private final LogErrorRepository logErrorRepository;

    private static final List<Class<?>> IGNORE_EXCEPTION = List.of(
        NotFoundException.class, NotMatchedException.class
    );

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) throws Exception {
        if (!IGNORE_EXCEPTION.contains(e.getClass())) {
            logErrorRepository.save(new LogError(e));
        }
        throw e;
    }

}
