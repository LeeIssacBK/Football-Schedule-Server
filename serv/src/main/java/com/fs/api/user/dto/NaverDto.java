package com.fs.api.user.dto;

import lombok.Data;

public class NaverDto {

    @Data
    public static class Auth {
        private String id;
        private String name;
        private String nickname;
        private String email;
        private String profileImage;
    }

}
