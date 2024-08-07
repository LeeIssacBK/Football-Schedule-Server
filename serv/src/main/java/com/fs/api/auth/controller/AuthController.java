package com.fs.api.auth.controller;

import com.fs.api.auth.dto.TokenDto;
import com.fs.api.auth.util.TokenProvider;
import com.fs.api.user.dto.NaverDto;
import com.fs.api.user.dto.UserDto;
import com.fs.api.user.service.SocialService;
import com.fs.configs.security.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "1. 인증", description = "로그인, 소셜, 토큰연장")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/oauth")
public class AuthController {

    private final TokenProvider tokenProvider;
    @Qualifier("kakao")
    private final SocialService socialKakaoService;
    @Qualifier("naver")
    private final SocialService socialNaverService;

    @Operation(summary = "관리자 로그인")
    @PostMapping("/login")
    public TokenDto.Token login(@RequestBody TokenDto.Login login) {
        return tokenProvider.login(login);
    }

    @Operation(summary = "flutter 카카오 로그인")
    @GetMapping("/kakao")
    public TokenDto.Token flutterKakaoLogin(@RequestParam String token) {
        return socialKakaoService.login(token);
    }

    @Operation(summary = "flutter 네이버 로그인")
    @PostMapping("/naver")
    public TokenDto.Token flutterNaverLogin(@RequestBody Map request) {
        return socialNaverService.login(request);
    }

    @Operation(summary = "로그아웃(리프레쉬토큰 삭제)")
    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestHeader("RefreshToken") String refreshToken) {
        tokenProvider.deleteToken(refreshToken);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "토큰연장")
    @PostMapping("/reissue")
    public TokenDto.Token reissue(@RequestHeader("RefreshToken") String refreshToken){
        return tokenProvider.reissue(refreshToken);
    }

    @Operation(summary = "내 정보 보기")
    @Secured({"ROLE_USER"})
    @GetMapping("/me")
    public UserDto.Simple getMe(@UserPrincipal UserDto.Simple user) {
        return user;
    }

}
