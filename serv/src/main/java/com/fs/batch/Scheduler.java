package com.fs.batch;

import com.fs.api.football.service.CountryService;
import com.fs.api.football.service.SeasonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "99. 스케쥴러 테스트")
@RequestMapping(path = "/api/schedule")
@RestController
public class Scheduler {

    private final CountryService countryService;
    private final SeasonService seasonService;

    //@Scheduled(cron = "0 0 0 * * *")
    @GetMapping("/country")
    void updateCountry() {
        countryService.update();
    }

    @GetMapping("/season")
    void updateSeason() {
        seasonService.update();
    }

}
