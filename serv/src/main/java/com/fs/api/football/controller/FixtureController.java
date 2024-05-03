package com.fs.api.football.controller;

import com.fs.api.football.dto.FixtureDto;
import com.fs.api.football.service.FixtureService;
import com.fs.api.user.dto.UserDto;
import com.fs.configs.security.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "5. 경기일정", description = "경기일정 정보")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/fixture")
public class FixtureController {

    private final FixtureService fixtureService;

    @Operation(summary = "팀 아이디를 통해 현재 시즌 팀의 경기 정보를 가져온다.")
    @GetMapping
    public List<FixtureDto.AppResponse> get(@RequestParam long teamId) {
        return fixtureService.get(teamId);
    }

    @Operation(summary = "유저 아이디를 통해 구독한 팀들의 경기 정보를 가져온다.")
    @GetMapping("/subscribe")
    public List<FixtureDto.AppResponse> get(@UserPrincipal UserDto.Simple user) {
        return fixtureService.get(user);
    }

}
