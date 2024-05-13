package com.fs.api.alert.controller;

import com.fs.api.alert.dto.AlertDto;
import com.fs.api.alert.service.AlertService;
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

@Tag(name = "6. 경기알람", description = "알람 정보")
@Secured({"ROLE_USER"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/alert")
public class AlertController {

    private final AlertService alertService;

    @Operation(summary = "등록된 모든 알람 목록을 확인한다.")
    @GetMapping
    public ResponseEntity<List<AlertDto.Response>> getAlerts(@UserPrincipal UserDto.Simple user) {
        return ResponseEntity.ok(alertService.getAlerts(user));
    }

    @Operation(summary = "fixture_id 를 통해 알람을 등록한다.")
    @PostMapping
    public ResponseEntity<?> saveAlert(@UserPrincipal UserDto.Simple user, @RequestParam long fixtureId) {
        alertService.saveAlert(user, fixtureId);
        return ResponseEntity.ok().build();
    }



}
