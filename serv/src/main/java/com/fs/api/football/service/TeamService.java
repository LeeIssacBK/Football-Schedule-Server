package com.fs.api.football.service;

import com.fs.api.football.domain.*;
import com.fs.api.football.dto.TeamDto;
import com.fs.api.football.dto.TeamDtoMapper;
import com.fs.api.football.dto.TeamStatisticsDto;
import com.fs.api.football.dto.TeamStatisticsDtoMapper;
import com.fs.api.football.util.ApiProvider;
import com.fs.common.enums.URL;
import com.fs.common.exceptions.BadRequestException;
import com.fs.common.exceptions.NotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.fs.api.football.domain.QFixture.fixture;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeamService {

    private final WebClient webClient;
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final TeamStatisticsRepository teamStatisticsRepository;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void update(long leagueId) {
        League league = leagueRepository.findByApiId(leagueId).orElseThrow(() -> new NotFoundException("league"));
        Season season = seasonRepository.findByLeagueAndCurrentIsTrue(league).orElseThrow(() -> new NotFoundException("season"));
        UriComponents url = UriComponentsBuilder.fromUriString(URL.FOOTBALL_API.getValue())
                .path("/teams")
                .queryParam("league", leagueId)
                .queryParam("season", season.getYear())
                .build();
        webClient.get()
                .uri(url.toUri())
                .headers(ApiProvider.getHeader())
                .retrieve()
                .bodyToMono(TeamDto.class)
                .subscribe(data -> {
                    data.getResponse().forEach(response -> {
                        TeamDto.Team teamResponse = response.getTeam();
                        TeamDto.Venue venueResponse = response.getVenue();
                        try {
                            teamRepository.findByApiId(teamResponse.getId()).ifPresentOrElse(team -> {
                                team.setName(teamResponse.getName());
                                team.setCode(teamResponse.getCode());
                                team.setFounded(teamResponse.getFounded());
                                team.setNational(teamResponse.isNational());
                                team.setLogo(teamResponse.getLogo());
                                team.setStadium(venueResponse.getName());
                                team.setAddress(venueResponse.getAddress());
                                team.setCity(venueResponse.getCity());
                                team.setCapacity(venueResponse.getCapacity());
                                team.setStadiumImage(venueResponse.getImage());
                            }, () -> {
                                teamRepository.save(Team.builder()
                                                .league(league)
                                                .season(season)
                                                .apiId(teamResponse.getId())
                                                .name(teamResponse.getName())
                                                .code(teamResponse.getCode())
                                                .founded(teamResponse.getFounded())
                                                .national(teamResponse.isNational())
                                                .logo(teamResponse.getLogo())
                                                .stadium(venueResponse.getName())
                                                .address(venueResponse.getAddress())
                                                .city(venueResponse.getCity())
                                                .capacity(venueResponse.getCapacity())
                                                .stadiumImage(venueResponse.getImage())
                                        .build());
                            });
                        } catch (Exception e) {
                            log.error("error team ::: " + teamResponse.getId() + ":" + teamResponse.getName());
                            log.error(e.getMessage());
                        }
                    });
            }, error -> {
                throw new BadRequestException(error.getMessage());
        });
    }

    @Transactional
    public void updateStatistics() {
        queryFactory.selectDistinct(fixture.league).from(fixture).stream().forEach(league -> {
            try {
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
                                log.warn(data.toString());
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
            } catch (Exception e) {
                //do nothing...
            }
        });


    }

    public List<TeamDto.AppResponse> get(long leagueId) {
        League league = leagueRepository.findByApiId(leagueId).orElseThrow(() -> new NotFoundException("league"));
        Season season = seasonRepository.findByLeagueAndCurrentIsTrue(league).orElseThrow(() -> new NotFoundException("season"));
        return TeamDtoMapper.INSTANCE.toAppResponse(teamRepository.findAllByLeagueAndSeason(league, season).orElseThrow(() -> new NotFoundException("teams")));
    }

}
