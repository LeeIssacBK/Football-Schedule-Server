package com.fs.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TokenDto {

    @Data
    public static class Login{
        private String userId;
        private String password;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Token{
        private String accessToken;
        private String refreshToken;
    }

}
