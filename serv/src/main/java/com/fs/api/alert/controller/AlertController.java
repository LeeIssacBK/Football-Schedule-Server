package com.fs.api.alert.controller;

import com.fs.api.alert.service.AlertService;
import com.fs.api.user.dto.UserDto;
import com.fs.configs.security.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "6. 경기알람", description = "알람 정보")
@Secured({"ROLE_USER"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/alert")
public class AlertController {

    private final AlertService alertService;

    @Operation(summary = "팀 아이디를 통해 현재 시즌 팀의 경기 정보를 가져온다.")
    @PostMapping
    public ResponseEntity<?> saveAlert(@UserPrincipal UserDto.Simple user, @RequestParam long fixtureId) {
        return alertService.saveAlert(user, fixtureId);
    }

}
