package com.fs.api.football.controller;

import com.fs.api.football.dto.SubscribeDto;
import com.fs.api.football.service.SubscribeService;
import com.fs.api.user.dto.UserDto;
import com.fs.common.enums.SubscribeType;
import com.fs.configs.security.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "6. 구독", description = "구독, 구독취소")
@Secured("ROLE_USER")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/subscribe")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @Operation(summary = "구독 목록")
    @GetMapping("/")
    public ResponseEntity<List<SubscribeDto.Response>> get(@UserPrincipal UserDto.Simple user, @RequestParam SubscribeType type) {
        return ResponseEntity.ok(subscribeService.get(user, type));
    }

    @Operation(summary = "구독")
    @PostMapping("/")
    public ResponseEntity<?> subscribe(@UserPrincipal UserDto.Simple user, @RequestBody SubscribeDto.Request request) {
        subscribeService.subscribe(user, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "구독취소")
    @DeleteMapping
    public ResponseEntity<?> unSubscribe(@UserPrincipal UserDto.Simple user, @RequestBody SubscribeDto.Request request) {
        return subscribeService.unSubscribe(user, request);
    }

}
