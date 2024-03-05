package com.fs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpirationEnums {
    ACCESS_TOKEN_EXPIRATION_TIME("30 seconds", 1000L * 10 * 3),
//    ACCESS_TOKEN_EXPIRATION_TIME("5 minutes", 1000L * 60 * 5),
    REFRESH_TOKEN_EXPIRATION_TIME("30 minutes", 1000L * 60 * 30);

    private String description;
    private Long value;
}
