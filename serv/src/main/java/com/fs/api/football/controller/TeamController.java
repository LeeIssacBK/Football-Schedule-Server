package com.fs.api.football.controller;

import com.fs.api.football.dto.TeamDto;
import com.fs.api.football.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "5. 팀")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/team")
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "팀")
    @GetMapping
    public List<TeamDto.AppResponse> get(@RequestParam long leagueId) {
        return teamService.get(leagueId);
    }


}
