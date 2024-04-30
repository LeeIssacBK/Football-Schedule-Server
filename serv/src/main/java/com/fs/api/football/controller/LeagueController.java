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

@Tag(name = "3. 리그", description = "리그 정보")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/league")
public class LeagueController {

    private final LeagueService leagueService;

    @Operation(summary = "국가코드를 입력받아 해당 국가의 리그정보를 가져온다.")
    @GetMapping
    public List<LeagueDto.AppResponse> get(@RequestParam String countryCode) {
        return leagueService.getWithFixture(countryCode);
    }


}
