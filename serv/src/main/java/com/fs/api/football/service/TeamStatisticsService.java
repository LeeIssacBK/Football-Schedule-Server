package com.fs.api.football.service;

import com.fs.api.football.domain.*;
import com.fs.api.football.dto.TeamStatisticsDto;
import com.fs.api.football.dto.TeamStatisticsDtoMapper;
import com.fs.api.football.util.ApiProvider;
import com.fs.common.enums.URL;
import com.fs.common.exceptions.NotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.fs.api.football.domain.QFixture.fixture;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class TeamStatisticsService {

    private final WebClient webClient;
    private final TeamStatisticsRepository teamStatisticsRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void updateStatistics() {
        queryFactory.selectDistinct(fixture.league).from(fixture).stream().forEach(league -> {
            Season season = seasonRepository.findByLeagueAndCurrentIsTrue(league).orElseThrow(() -> new NotFoundException("season"));
            List<Team> teams = teamRepository.findAllByLeagueAndSeason(league, season).orElseThrow(() -> new NotFoundException("teams"));
            teams.forEach(team -> {
                UriComponents url = UriComponentsBuilder.fromUriString(URL.FOOTBALL_API.getValue())
                        .path("/teams/statistics")
                        .queryParam("league", league.getApiId())
                        .queryParam("season", season.getYear())
                        .queryParam("team", team.getApiId())
                        .build();
                webClient.get()
                        .uri(url.toUri())
                        .headers(ApiProvider.getHeader())
                        .retrieve()
                        .bodyToMono(TeamStatisticsDto.class)
                        .subscribe(data -> {
                            TeamStatisticsDto.Response response = data.getResponse();
                            teamStatisticsRepository.findByTeam(team).ifPresentOrElse(
                                    teamStatistics -> {
                                        TeamStatisticsDtoMapper.INSTANCE.update(response, teamStatistics);
                                    },
                                    () -> {
                                        TeamStatistics teamStatistics = TeamStatisticsDtoMapper.INSTANCE.toEntity(response);
                                        teamStatistics.setTeam(team);
                                        teamStatisticsRepository.save(teamStatistics);
                                    }
                            );
                        });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //do nothing...
                }
            });
        });
    }

}
