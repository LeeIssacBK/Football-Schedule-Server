package com.fs.api.user.dto;

import lombok.Data;

public class KakaoDto {

    @Data
    public static class AccessToken {
        private String token_type;
        private String scope;
        private String access_token;
        private long expires_in;
        private String refresh_token;
        private long refresh_token_expires_in;
    }

    @Data
    public static class Auth {
        private long id;
        private String connected_at;
        private Properties properties;
        private Account kakao_account;
    }

    @Data
    public static class Account {
        private boolean profile_nickname_needs_agreement;
        private boolean profile_image_needs_agreement;
        private Profile profile;
    }

    @Data
    public static class Profile {
        private String nickname;
        private String thumbnail_image_url;
        private String profile_image_url;
        private boolean is_default_image;
    }

    @Data
    public static class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }


}
