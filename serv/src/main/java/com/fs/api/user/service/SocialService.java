package com.fs.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocialService {

    private final String KAKAO_URL = "";
    private final WebClient webClient;

    public String getKakaoToken(String code) {

        UriComponents url = UriComponentsBuilder.fromUriString(KAKAO_URL)
                .queryParam("grant_type","")
                .queryParam("client_id","")
                .queryParam("client_secret","")
                .queryParam("redirect_uri","")
                .queryParam("code",code)
                .build();

        webClient.post()
                .uri(url.toUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        return "";
    }

}
