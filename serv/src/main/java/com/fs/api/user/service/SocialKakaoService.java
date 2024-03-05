package com.fs.api.user.service;

import com.fs.api.auth.dto.TokenDto;
import com.fs.api.auth.util.TokenProvider;
import com.fs.api.user.domain.User;
import com.fs.api.user.dto.KakaoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocialKakaoService implements SocialService {

    private final String KAKAO_AUTH_URL = "https://kauth.kakao.com";
    private final String KAKAO_API_URL = "https://kapi.kakao.com";
    private final WebClient webClient;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Override
    public TokenDto.Token login(String code) {
        //1. 카카오 액세스 토큰발급
        String kakaoToken = getKakaoToken(code).block();
        //2. 카카오 유저정보 확인
        Optional<KakaoDto.Auth> auth = getKakaoAuth(kakaoToken).blockOptional();
        if (auth.isPresent()) {
            //3. 회원가입이력 확인
            User user = userService.createOrFindByUser(auth.get());
            //4. 서비스 토큰 발급
            return tokenProvider.login(TokenDto.Login.builder()
                            .userId(user.getUserId())
                            .password(auth.get().getId() + auth.get().getConnected_at())
                    .build());
        }
        throw new RuntimeException();
    }


    private Mono<String> getKakaoToken(String code) {
        UriComponents url = UriComponentsBuilder.fromUriString(KAKAO_AUTH_URL)
                .path("/oauth/token")
                .queryParam("grant_type","authorization_code")
                .queryParam("client_id","be34591b9514798eec7010a400ecca1a")
                .queryParam("client_secret","RxqHajv4PTFGnSaR21jW87RWhRASb6Wk")
                .queryParam("redirect_uri","http://localhost:8090/oauth/kakao")
                .queryParam("code",code)
                .build();
        return webClient.post()
                .uri(url.toUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .bodyToMono(KakaoDto.AccessToken.class)
                .map(KakaoDto.AccessToken::getAccess_token);
    }

    private Mono<KakaoDto.Auth> getKakaoAuth(String token) {
        UriComponents url = UriComponentsBuilder.fromUriString(KAKAO_API_URL)
                .path("/v2/user/me")
                .build();
        return webClient.post()
                .uri(url.toUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(KakaoDto.Auth.class);
    }

}