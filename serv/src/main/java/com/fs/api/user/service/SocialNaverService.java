package com.fs.api.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fs.api.auth.dto.TokenDto;
import com.fs.api.auth.util.TokenProvider;
import com.fs.api.user.domain.User;
import com.fs.api.user.dto.NaverDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Qualifier("naver")
@Slf4j
@RequiredArgsConstructor
@Service
public class SocialNaverService implements SocialService {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final ObjectMapper mapper;

    @Override
    public TokenDto.Token login(String code) {
        return null;
    }

    @Override
    public TokenDto.Token login(Map map) {
        NaverDto.Auth auth = mapper.convertValue(map, NaverDto.Auth.class);
        //1. 회원가입이력 확인
        User user = userService.createOrFindByUser(auth);
        //2. 서비스 토큰 발급
        return tokenProvider.login(TokenDto.Login.builder()
                        .userId(user.getUserId())
                        .password(auth.getId() + auth.getEmail())
                .build());
    }

}
