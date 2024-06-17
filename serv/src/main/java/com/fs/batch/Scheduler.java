package com.fs.batch;

import com.fs.api.alert.dto.AlertDto;
import com.fs.api.alert.service.AlertService;
import com.fs.api.alert.service.FirebaseService;
import com.fs.api.football.service.*;
import com.fs.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("prod")
@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {

    private final LeagueService leagueService;
    private final FixtureService fixtureService;
    private final TeamStatisticsService teamStatisticsService;
    private final TeamStandingService teamStandingService;
    private final FirebaseService firebaseService;
    private final AlertService alertService;
    private final UserService userService;

    //전세계 시즌 정보를 업데이트한다.
    @Scheduled(cron = "0 0 0 1 * ?")
    void updateSeason() {
        leagueService.update();
    }

    //경기일정을 가지고 있는 모든 리그를 최신화한다.
    @Scheduled(cron = "0 0 0 1 * ?")
    void update() {
        fixtureService.update();
    }

    //팀의 리그 순위 및 최근경기 결과 정보를 최신화한다.
    @Scheduled(cron = "0 0 0 * * ?")
    void updateStanding() {
        teamStandingService.updateStanding();
    }

    //경기일정 정보를 가지고 있는 팀 통계정보를 최신화한다.
    @Scheduled(cron = "0 0 0 * * ?")
    void updateStatistics() {
        teamStatisticsService.updateStatistics();
    }

    //메세지 전송
    @Scheduled(cron = "0 * * * * ?")
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

    //탈퇴 대기회원 탈퇴
    @Scheduled(cron = "0 0 0 * * ?")
    void updateWithdrawUser() {
        userService.deleteWithdrawUser();
    }

}
