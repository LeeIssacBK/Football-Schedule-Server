package com.fs.api.user.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("logoutAccessToken")
@AllArgsConstructor
@Builder
public class LogoutAccessToken {

    @Id
    private String id;

    private String userId;

    @TimeToLive
    private Long expiration;

    public static LogoutAccessToken createRefreshToken(String userId, String accessToken, Long remainingMilliSeconds){
        return LogoutAccessToken.builder()
                .id(accessToken)
                .userId(userId)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }

}
