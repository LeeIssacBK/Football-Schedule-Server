package com.fs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpirationEnums {

//    ACCESS_TOKEN_EXPIRATION_TIME("ACCESS TOKEN / 30 SECONDS", 1000L * 30),
//    REFRESH_TOKEN_EXPIRATION_TIME("REFRESH TOKEN / 1 MINUTES", 1000L * 60);

    ACCESS_TOKEN_EXPIRATION_TIME("ACCESS TOKEN / 5 MINUTES", 1000L * 60 * 5),
    REFRESH_TOKEN_EXPIRATION_TIME("REFRESH TOKEN / 30 MINUTES", 1000L * 60 * 30);

    private String description;
    private Long value;

}
