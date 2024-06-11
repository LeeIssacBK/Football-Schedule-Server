package com.fs.api.user.controller;

import com.fs.api.user.dto.DeviceDto;
import com.fs.api.user.dto.UserDto;
import com.fs.api.user.service.DeviceService;
import com.fs.configs.security.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "0. 디바이스", description = "디바이스 정보 등록, 확인")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/device")
public class DeviceController {

    private final DeviceService deviceService;

    @Operation(summary = "디바이스 정보 등록")
    @PostMapping()
    public ResponseEntity save(@UserPrincipal UserDto.Simple user, @RequestBody DeviceDto.Request request) {
        return deviceService.save(user.getUserId(), request);
    }


}
