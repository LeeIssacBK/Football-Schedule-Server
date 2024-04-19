package com.fs.api.football.service;

import com.fs.api.football.domain.*;
import com.fs.api.football.dto.FixtureDto;
import com.fs.api.football.dto.FixtureDtoMapper;
import com.fs.api.football.util.ApiProvider;
import com.fs.common.enums.URL;
import com.fs.common.exceptions.BadRequestException;
import com.fs.common.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FixtureService {

    private final WebClient webClient;
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final FixtureRepository fixtureRepository;

    @Transactional
    public void update(long leagueId) {
        League league = leagueRepository.findByApiId(leagueId).orElseThrow(() -> new NotFoundException("league"));
        Season season = seasonRepository.findByLeagueAndCurrentIsTrue(league).orElseThrow(() -> new NotFoundException("season"));
        UriComponents url = UriComponentsBuilder.fromUriString(URL.FOOTBALL_API.getValue())
                .path("/fixtures")
                .queryParam("league", leagueId)
                .queryParam("season", season.getYear())
                .build();
        webClient.get()
                .uri(url.toUri())
                .headers(ApiProvider.getHeader())
                .retrieve()
                .bodyToMono(FixtureDto.class)
                .subscribe(data -> {
                    data.getResponse().forEach(response -> {
                        FixtureDto.Fixture fixtureResponse = response.getFixture();
                        FixtureDto.League leagueResponse = response.getLeague();
                        FixtureDto.Teams teamsResponse = response.getTeams();
                        FixtureDto.Goals goalsResponse = response.getGoals();
                        try {
                            fixtureRepository.findByApiId(fixtureResponse.getId()).ifPresentOrElse(fixture -> {
                                fixture.setRound(leagueResponse.getRound());
                                fixture.setStatus(Fixture.Status.parse(fixtureResponse.getStatus().get_short()));
                                fixture.setReferee(fixtureResponse.getReferee());
                                fixture.setDate(fixtureResponse.getDate().toLocalDateTime());
                                fixture.setHome(teamRepository.findByApiId(teamsResponse.getHome().getId()).orElseThrow(() -> new NotFoundException("home team")));
                                fixture.setAway(teamRepository.findByApiId(teamsResponse.getAway().getId()).orElseThrow(() -> new NotFoundException("away team")));
                                fixture.setHomeGoal(goalsResponse.getHome());
                                fixture.setAwayGoal(goalsResponse.getAway());
                                fixture.setMatchResult(Fixture.MatchResult.parse(teamsResponse));
                                fixtureRepository.save(fixture);
                            }, () -> {
                                fixtureRepository.save(Fixture.builder()
                                                .apiId(fixtureResponse.getId())
                                                .round(leagueResponse.getRound())
                                                .status(Fixture.Status.parse(fixtureResponse.getStatus().get_short()))
                                                .referee(fixtureResponse.getReferee())
                                                .date(fixtureResponse.getDate().toLocalDateTime())
                                                .league(league)
                                                .home(teamRepository.findByApiId(teamsResponse.getHome().getId()).orElseThrow(() -> new NotFoundException("home team")))
                                                .away(teamRepository.findByApiId(teamsResponse.getAway().getId()).orElseThrow(() -> new NotFoundException("away team")))
                                                .homeGoal(goalsResponse.getHome())
                                                .awayGoal(goalsResponse.getAway())
                                                .matchResult(Fixture.MatchResult.parse(teamsResponse))
                                        .build());

                            });
                        } catch (Exception e) {
                            log.error("error fixture ::: " + fixtureResponse.getId());
                            log.error(e.getMessage());
                        }
                    });
                }, error -> {
                    throw new BadRequestException(error.getMessage());
                });
    }

    public List<FixtureDto.AppResponse> get(long teamId) {
        Team team = teamRepository.findByApiId(teamId).orElseThrow(() -> new NotFoundException("team"));
        return FixtureDtoMapper.INSTANCE.toAppResponse(fixtureRepository.findAllByHomeOrAway(team, team).orElseThrow(() -> new NotFoundException("fixture")));
    }

}
