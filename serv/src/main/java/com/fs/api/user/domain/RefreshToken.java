package com.fs.api.user.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("refreshToken")
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @TimeToLive             //Redis 유효시간 관리
    private Long expiration;

    public static RefreshToken createRefreshToken(String userId, String refreshToken, Long remainingMilliSeconds){
        return RefreshToken.builder()
                .id(userId)
                .refreshToken(refreshToken)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }

}
