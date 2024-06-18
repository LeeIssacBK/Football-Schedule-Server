package com.fs.api.support.service;

import com.fs.api.support.domain.Support;
import com.fs.api.support.domain.SupportRepository;
import com.fs.api.support.dto.SupportDto;
import com.fs.api.user.domain.UserRepository;
import com.fs.api.user.dto.UserDto;
import com.fs.common.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class SupportService {

    private final UserRepository userRepository;
    private final SupportRepository supportRepository;

    public void submit(SupportDto.Request request, UserDto.Simple user) {
        supportRepository.save(
                Support.builder()
                        .type(request.getType())
                        .title(request.getTitle())
                        .content(request.getContent())
                        .createdBy(userRepository.findByUserId(user.getUserId())
                                .orElseThrow(() -> new NotFoundException("user")))
                        .build()
        );
    }

}
