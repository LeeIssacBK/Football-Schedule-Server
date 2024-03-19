package com.fs.api.football.controller;

import com.fs.api.football.dto.FixtureDto;
import com.fs.api.football.service.FixtureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "6. 경기일정")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/fixture")
public class FixtureController {

    private final FixtureService fixtureService;

    @GetMapping
    public List<FixtureDto.AppResponse> get(@RequestParam long teamId) {
        return fixtureService.get(teamId);
    }

}
