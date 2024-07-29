package com.fs.api.user.service;

import com.fs.api.auth.dto.TokenDto;
import com.fs.api.auth.util.TokenProvider;
import com.fs.api.user.domain.User;
import com.fs.api.user.dto.KakaoDto;
import com.fs.common.enums.URL;
import com.fs.common.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Qualifier("kakao")
@Slf4j
@RequiredArgsConstructor
@Service
public class SocialNaverService implements SocialService {

    private final WebClient webClient;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Override
    public TokenDto.Token login(String token) {
        Optional<KakaoDto.Auth> auth = getAuth(token);
        if (auth.isPresent()) {
            //1. 회원가입이력 확인
            User user = userService.createOrFindByUser(auth.get());
            //2. 서비스 토큰 발급
            return tokenProvider.login(TokenDto.Login.builder()
                            .userId(user.getUserId())
                            .password(auth.get().getId() + auth.get().getConnected_at())
                    .build());
        }
        throw new BadRequestException("kakao");
    }

    private Optional<KakaoDto.Auth> getAuth(String token) {
        UriComponents url = UriComponentsBuilder.fromUriString(URL.KAKAO_API.getValue())
                .path("/v2/user/me")
                .build();
        return webClient.post()
                .uri(url.toUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(KakaoDto.Auth.class)
                .blockOptional();
    }

}
