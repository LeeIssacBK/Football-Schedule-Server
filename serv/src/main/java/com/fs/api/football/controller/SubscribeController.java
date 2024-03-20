package com.fs.api.football.controller;

import com.fs.api.football.dto.LeagueDto;
import com.fs.api.football.service.SubscribeService;
import com.fs.api.user.dto.UserDto;
import com.fs.configs.security.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "6. 구독", description = "구독, 구독취소")
@Secured({"ROLE_USER"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/subscribe")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @Operation(summary = "구독")
    @PostMapping("/")
    public ResponseEntity<?> subscribe(@UserPrincipal UserDto.Simple user) {

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "구독취소")
    @DeleteMapping("/")
    public ResponseEntity<?> unSubscribe(@UserPrincipal UserDto.Simple user) {
        return ResponseEntity.ok().build();
    }


}
