package com.fs.api.football.controller;

import com.fs.api.football.dto.CountryDto;
import com.fs.api.football.service.CountryService;
import com.fs.common.enums.Continent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "2. 국가", description = "국가정보")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/country")
public class CountryController {

    private final CountryService countryService;

    @Operation(summary = "경기정보를 가지고 있는 국가 정보만 불러온다")
    @GetMapping
    public List<CountryDto.AppResponse> get(@RequestParam Continent continent) {
        return countryService.getWithFixture(continent);
    }

}
