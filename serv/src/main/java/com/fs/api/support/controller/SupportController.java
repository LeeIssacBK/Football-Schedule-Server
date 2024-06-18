package com.fs.api.support.controller;

import com.fs.api.support.dto.SupportDto;
import com.fs.api.support.service.SupportService;
import com.fs.api.user.dto.UserDto;
import com.fs.configs.security.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "8. 문의", description = "문의하기")
@Secured("ROLE_USER")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/support")
public class SupportController {

    private final SupportService supportService;

    @Operation(summary = "고객 문의 내용을 저장한다.")
    @PostMapping
    public ResponseEntity submit(@UserPrincipal UserDto.Simple user, @RequestBody SupportDto.Request request) {
        supportService.submit(request, user);
        return ResponseEntity.ok().build();
    }

}
