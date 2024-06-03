package com.fs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpirationEnums {
    ACCESS_TOKEN_EXPIRATION_TIME("1 day", 1000L * 60 * 60 * 24),
    REFRESH_TOKEN_EXPIRATION_TIME("7 days", 1000L * 60 * 60 * 24 * 7);
    private String description;
    private long value;
}
