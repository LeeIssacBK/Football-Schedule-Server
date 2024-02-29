package com.fs.api.auth.controller;

import com.fs.api.auth.dto.TokenDto;
import com.fs.api.auth.util.TokenProvider;
import com.fs.api.user.dto.UserDto;
import com.fs.api.user.service.SocialService;
import com.fs.configs.security.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Tag(name = "1. 인증")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final TokenProvider tokenProvider;
    private SocialService socialService;

    @Operation(summary = "관리자 로그인")
    @PostMapping("/login")
    public TokenDto.Token login(@RequestBody TokenDto.Login login) {
        return tokenProvider.login(login);
    }

    @GetMapping("/kakao")
    public ResponseEntity<TokenDto.Token> kakaoLogin(@RequestParam String code) {
        //1. 카카오 토큰발급
        String kakaoToken  = socialService.getKakaoToken(code);
        //2. 카카오 유저정보 확인

        //3. 회원가입이력 확인

        //4. 서비스 토큰 발급

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto.Token> reissue(@RequestHeader("RefreshToken") String refreshToken){
        return ResponseEntity.ok(tokenProvider.reissue(refreshToken));
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/me")
    public ResponseEntity<?> getMe(@UserPrincipal UserDto.Simple user) {
        return ResponseEntity.ok(user);
    }

}
