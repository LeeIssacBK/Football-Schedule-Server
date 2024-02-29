package com.fs.api.auth.util;

import com.fs.api.auth.domain.RefreshToken;
import com.fs.api.auth.repository.RefreshTokenRepository;
import com.fs.api.auth.dto.TokenDto;
import com.fs.api.user.domain.User;
import com.fs.api.user.repository.UserRepository;
import com.fs.common.enums.JwtExpirationEnums;
import com.fs.configs.security.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Transactional
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final RefreshTokenRepository refreshTokenRepository;


    public TokenDto.Token login(TokenDto.Login login) {
        User user = userRepository.findByUserId(login.getUserId()).orElseThrow();
        checkPassword(login.getPassword(), user.getPassword());

        String accessToken = jwtConfig.generateToken(user, JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME);
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
        return refreshTokenRepository.save(RefreshToken.createRefreshToken(user.getUserId(), jwtConfig.generateToken(user, JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME), 10000L));
    }


    public TokenDto.Token reissue(String refreshToken) {
        User user = getCurrentuser();
        RefreshToken redisRefreshToken = refreshTokenRepository.findById(user.getUserId()).orElseThrow(NoSuchElementException::new);
        if (refreshToken.equals(redisRefreshToken.getRefreshToken())) {
            return reissueRefreshToken(refreshToken, user);
        }
        throw new IllegalArgumentException("not matched token");
    }

    private User getCurrentuser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUserId(principal.getUsername()).orElseThrow(NoSuchElementException::new);
    }

    private TokenDto.Token reissueRefreshToken(String refreshToken, User user) {
        String accessToken = jwtConfig.generateToken(user, JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME);
        if (lessThanReissueExpirationTimesLeft(refreshToken)) {
            return TokenDto.Token.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            return TokenDto.Token.builder()
                    .accessToken(accessToken)
                    .refreshToken(saveRefreshToken(user).getRefreshToken())
                    .build();
        }
    }

    private boolean lessThanReissueExpirationTimesLeft(String refreshToken) {
        return jwtConfig.getRemainMilliSeconds(refreshToken) < JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getValue();
    }

}
