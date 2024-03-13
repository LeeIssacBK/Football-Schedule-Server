package com.fs.api.football.service;

import com.fs.api.football.domain.*;
import com.fs.api.football.dto.TeamDto;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class TeamService {

    private final WebClient webClient;
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;

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
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "81fe2c5be0msh192ce0f95db9856p1c396ejsn2af5c49a0a78")
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
                            log.error("error team ::: " + response.getTeam().getId() + ":" + response.getTeam().getName());
                            log.error(e.getMessage());
                        }
                    });
            }, error -> {
                throw new BadRequestException(error.getMessage());
        });
    }

}
