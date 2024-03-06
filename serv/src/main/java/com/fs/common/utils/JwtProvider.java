package com.fs.common.utils;

import com.fs.api.user.domain.User;
import com.fs.common.enums.JwtExpirationEnums;
import com.fs.configs.property.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final AppProperties appProperties;

    public String generateToken(User user, JwtExpirationEnums expire) {
        Date now = new Date();
        List<String> scope = new ArrayList<>();
        scope.add("any");
        Claims claims = Jwts.claims();   //JWT payload 에 저장되는 정보단위
        claims.setId(UUID.randomUUID().toString());
        claims.put("user_name", user.getUserId());
        claims.put("authorities", user.getRoles());  //정보는 key : value 으로 저장
        claims.put("client_id", appProperties.getOauth().getClientId());
        claims.put("scope", scope);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)  //정보주입 (userId + roles)
                .setIssuedAt(now)   //발행시각
                .setExpiration(new Date(System.currentTimeMillis() + expire.getValue()))    //만료시간
                .signWith(SignatureAlgorithm.HS256, appProperties.getOauth().getTokenSigningKey())  //토큰의 암호화 알고리즘
                .compact();
    }

    public Jws<Claims> getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(appProperties.getOauth().getTokenSigningKey())
                .parseClaimsJws(token);
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = getClaims(token);
        if (claims != null) {
            return !claims.getBody().getExpiration().before(new Date());
        }
        return false;
    }

    public long getRemainMilliSeconds(String token) {
        Date expiration = getClaims(token).getBody().getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

}
