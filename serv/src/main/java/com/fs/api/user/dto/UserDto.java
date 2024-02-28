package com.fs.api.user.dto;

import lombok.Data;

public class UserDto {

    @Data
    public static class Simple {

        private Long id;
        private String userName;

    }

}
