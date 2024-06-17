package com.fs.api.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

public class UserDto {

    @Data
    public static class Simple {
        private String status;
        private String userId;
        private String name;
        private String profileImage;
        private LocalDateTime createdAt;
    }

}
