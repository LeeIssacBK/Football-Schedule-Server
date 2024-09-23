package com.fs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpirationEnums {
    ACCESS_TOKEN_EXPIRATION_TIME("7 day", 1000L * 60 * 60 * 24 * 7),
    REFRESH_TOKEN_EXPIRATION_TIME("30 days", 1000L * 60 * 60 * 24 * 30);
    private String description;
    private long value;
}
