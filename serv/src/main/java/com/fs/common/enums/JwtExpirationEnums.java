package com.fs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpirationEnums {
    ACCESS_TOKEN_EXPIRATION_TIME("30 minutes", 1000L * 60 * 30),
    REFRESH_TOKEN_EXPIRATION_TIME("2 hours", 1000L * 60 * 60 * 2);
    private String description;
    private long value;
}
