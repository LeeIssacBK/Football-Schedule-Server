package com.fs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum URL {
    KAKAO_AUTH("https://kauth.kakao.com"),
    KAKAO_API("https://kapi.kakao.com"),
    FOOTBALL_API("https://api-football-v1.p.rapidapi.com/v3");
    private final String value;
}
