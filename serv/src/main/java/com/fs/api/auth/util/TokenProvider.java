package com.fs.api.auth.util;

import com.fs.api.auth.domain.RefreshToken;
import com.fs.api.auth.dto.TokenDto;
import com.fs.api.auth.repository.RefreshTokenRepository;
import com.fs.api.user.domain.User;
import com.fs.api.user.repository.UserRepository;
import com.fs.common.enums.JwtExpirationEnums;
import com.fs.common.utils.JwtProvider;
import com.fs.common.utils.RedisProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisProvider redisProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    public TokenDto.Token login(TokenDto.Login login) {
        User user = userRepository.findByUserId(login.getUserId()).orElseThrow();
        checkPassword(login.getPassword(), user.getPassword());
        String accessToken = jwtProvider.generateToken(user, JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME);
        RefreshToken refreshToken = saveRefreshToken(user);
        return TokenDto.Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public void checkPassword(String password1, String password2) {
        if (!passwordEncoder.matches(password1, password2)) {
            throw new IllegalArgumentException("not matched password");
        }
    }

    private RefreshToken saveRefreshToken(User user) {
        return refreshTokenRepository.save(
                RefreshToken.createRefreshToken(
                        jwtProvider.generateToken(user, JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME)
                        , user.getUserId()
                        , JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getValue()));
    }

    public TokenDto.Token reissue(String refreshToken) {
        RefreshToken redisRefreshToken = refreshTokenRepository.findById(refreshToken).orElseThrow(() -> new RuntimeException("not found refresh token"));
        User user = userRepository.findByUserId(redisRefreshToken.getUserId()).orElseThrow(() -> new RuntimeException("not found user"));
        return reissueRefreshToken(refreshToken, user);
    }

    private TokenDto.Token reissueRefreshToken(String refreshToken, User user) {
        TokenDto.Token token = TokenDto.Token.builder()
                .accessToken(jwtProvider.generateToken(user, JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME))
                .refreshToken(refreshToken)
                .build();
        if (lessThanReissueExpirationTimesLeft(refreshToken)) {
            token.setRefreshToken(saveRefreshToken(user).getRefreshToken());
            refreshTokenRepository.deleteById(refreshToken);
        }
        return token;
    }

    private boolean lessThanReissueExpirationTimesLeft(String refreshToken) {
        return jwtProvider.getRemainMilliSeconds(refreshToken) <= 0;
    }

}
