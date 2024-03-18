package com.fs.api.football.controller;

import com.fs.api.football.dto.LeagueDto;
import com.fs.api.football.service.LeagueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "4. 리그")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/league")
public class LeagueController {

    private final LeagueService leagueService;

    @Operation(summary = "리그")
    @GetMapping
    public List<LeagueDto.AppResponse> get(@RequestParam String countryCode) {
        return leagueService.get(countryCode);
    }


}
