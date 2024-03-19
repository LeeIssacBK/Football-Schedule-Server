package com.fs.batch;

import com.fs.api.football.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "99. 스케쥴러 트리거")
@RequestMapping(path = "/api/schedule")
@RestController
public class Scheduler {

    private final CountryService countryService;
    private final LeagueService leagueService;
    private final TeamService teamService;
    private final PlayerService playerService;
    private final FixtureService fixtureService;

    //@Scheduled(cron = "0 0 0 * * *")
    @GetMapping("/country")
    void updateCountry() {
        countryService.update();
    }

    @GetMapping("/league")
    void updateSeason() {
        leagueService.update();
    }

    @GetMapping("/team")
    void updateTeam(@RequestParam long leagueId) {
        teamService.update(leagueId);
    }

    @GetMapping("/player")
    void updatePlayer(@RequestParam long teamId) {
        playerService.update(teamId);
    }

    @GetMapping("/fixture")
    void updateFixture(@RequestParam long leagueId) {
        fixtureService.update(leagueId);
    }

}
