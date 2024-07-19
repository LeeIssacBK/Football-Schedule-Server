package com.fs.api.football.service;

import com.fs.api.football.domain.*;
import com.fs.api.football.dto.StandingDto;
import com.fs.api.football.dto.StandingDtoMapper;
import com.fs.api.football.util.ApiProvider;
import com.fs.api.log.domain.LogApi;
import com.fs.api.log.domain.LogApiRepository;
import com.fs.common.enums.LogApiType;
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

import java.util.Optional;

import static com.fs.api.football.domain.QFixture.fixture;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class TeamStandingService {


    private final WebClient webClient;
    private final StandingRepository standingRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final JPAQueryFactory queryFactory;
    private final LogApiRepository logApiRepository;

    @Transactional
    public void updateStanding() {
        queryFactory.selectDistinct(fixture.league).from(fixture).stream().forEach(league -> {
            try {
                Season season = seasonRepository.findByLeagueAndCurrentIsTrue(league).orElseThrow(() -> new NotFoundException("season"));
                UriComponents url = UriComponentsBuilder.fromUriString(URL.FOOTBALL_API.getValue())
                        .path("/standings")
                        .queryParam("league", league.getApiId())
                        .queryParam("season", season.getYear())
                        .build();
                webClient.get()
                        .uri(url.toUri())
                        .headers(ApiProvider.getHeader())
                        .retrieve()
                        .bodyToMono(StandingDto.class)
                        .subscribe(data -> {
                            logApiRepository.save(
                                    LogApi.builder()
                                            .type(LogApiType.STANDING)
                                            .requestData(url.toUriString())
                                            .responseData(data.getResponse().toString())
                                            .build()
                            );
                            data.getResponse().forEach(response -> {
                                Optional.of(response.getLeague().getStandings()).ifPresent(standings -> {
                                    standings.get(0).forEach(standing -> {
                                        standingRepository.findByTeamApiId(standing.getTeam().getId()).ifPresentOrElse(
                                                entity -> {
                                                    StandingDtoMapper.INSTANCE.update(standing, entity);
                                                }, () -> {
                                                    standingRepository.save(
                                                            Standing.builder()
                                                                    .team(teamRepository.save(teamRepository.findByApiId(standing.getTeam().getId()).orElseThrow(() -> new NotFoundException("team"))))
                                                                    ._rank(standing.get_rank())
                                                                    .points(standing.getPoints())
                                                                    .goalsDiff(standing.getGoalsDiff())
                                                                    ._group(standing.get_group())
                                                                    .form(standing.getForm())
                                                                    ._status(standing.get_status())
                                                                    .description(standing.getDescription())
                                                                    ._all(standing.get_all())
                                                                    .home(standing.getHome())
                                                                    .away(standing.getAway())
                                                                    .build());
                                                });
                                    });
                                });
                            });
                        });
                Thread.sleep(5000);
            } catch (Exception e) {
                //do nothing...
            }
        });
    }


    public StandingDto.AppResponse getStanding(long teamId) {
        Standing standing = standingRepository.findByTeamApiId(teamId).orElse(null);
        StandingDto.Response.Standing.Game _all = standing.get_all();
        StandingDto.Response.Standing.Game home = standing.getHome();
        StandingDto.Response.Standing.Game away = standing.getAway();
        return StandingDtoMapper.INSTANCE.toAppResponse(standing);
    }

}
