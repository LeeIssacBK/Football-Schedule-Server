package com.fs.api.user.controller;

import com.fs.api.user.dto.DeviceDto;
import com.fs.api.user.dto.MyInfoDto;
import com.fs.api.user.dto.UserDto;
import com.fs.api.user.service.MyInfoService;
import com.fs.configs.security.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@Tag(name = "7. 마이페이지", description = "마이페이지 정보")
@Secured("ROLE_USER")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/mypage")
public class MyInfoController {

    private final MyInfoService myInfoService;

    @Operation(summary = "내 정보")
    @GetMapping("/myinfo")
    public ResponseEntity<MyInfoDto.Response> getMyInfo(@UserPrincipal UserDto.Simple user) {
        return ResponseEntity.ok(myInfoService.getMyInfo(user));
    }

}
