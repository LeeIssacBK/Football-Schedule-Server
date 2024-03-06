package com.fs.api.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("refreshToken")
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    private String refreshToken;

    private String userId;

    @TimeToLive
    private Long expiration;

    public static RefreshToken createRefreshToken(String refreshToken, String userId, Long remainingMilliSeconds) {
        return RefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }

}
