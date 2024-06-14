package com.fs.batch;

import com.fs.api.alert.dto.AlertDto;
import com.fs.api.alert.service.AlertService;
import com.fs.api.alert.service.FirebaseService;
import com.fs.api.football.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Secured("ROLE_ADMIN")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "99. schedule trigger", description = "스케쥴러 테스트 트리거")
@RequestMapping(path = "/api/trigger")
@RestController
public class Trigger {

    private final CountryService countryService;
    private final LeagueService leagueService;
    private final TeamService teamService;
    private final PlayerService playerService;
    private final FixtureService fixtureService;
    private final TeamStatisticsService teamStatisticsService;
    private final TeamStandingService teamStandingService;
    private final FirebaseService firebaseService;
    private final AlertService alertService;

    @Operation(summary = "국가정보를 업데이트한다.")
    @GetMapping("/country")
    void updateCountry() {
        countryService.update();
    }

    @Operation(summary = "전세계 리그정보를 업데이트한다.")
    @GetMapping("/league")
    void updateSeason() {
        leagueService.update();
    }

    @Operation(summary = "리그 아이디를 통해 팀정보를 업데이트한다.")
    @GetMapping("/team")
    void updateTeam(@RequestParam long leagueId) {
        teamService.update(leagueId);
    }

    @Deprecated
    @Operation(summary = "팀 아이디를 통해 선수정보를 업데이트한다.")
    @GetMapping("/player")
    void updatePlayer(@RequestParam long teamId) {
        playerService.update(teamId);
    }

    @Operation(summary = "리그 아이디를 통해 경기일정을 업데이트한다.")
    @GetMapping("/fixture")
    void updateFixture(@RequestParam long leagueId) {
        fixtureService.update(leagueId);
    }

    @Operation(summary = "경기일정을 가지고 있는 모든 리그를 최신화한다.")
    @GetMapping("/fixture/auto")
    void update() {
        fixtureService.update();
    }

    @Operation(summary = "팀의 리그 순위 및 최근경기 결과 정보를 최신화한다.")
    @GetMapping("/update/standing")
    void updateStanding() {
        teamStandingService.updateStanding();
    }

    @Operation(summary = "경기일정 정보를 가지고 있는 팀 통계정보를 최신화한다.")
    @GetMapping("/update/statistics")
    void updateStatistics() {
        teamStatisticsService.updateStatistics();
    }

    @Operation(summary = "메세지 전송")
    @GetMapping("/message/send")
    void sendAlertMessage() {
        try {
            //step 1 - Alert의 isSend 가 false 이고 send time 이 현재시간과 같은 데이터 추출.
            List<AlertDto.Message> alerts = alertService.getAlertMessages();

            //step 2 - firebase admin sdk 를 통해 디바이스에 메세지를 전송한다.
            if (!alerts.isEmpty()) {
                firebaseService.send(alerts);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
