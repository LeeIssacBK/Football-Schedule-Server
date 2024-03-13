package com.fs.api.football.service;

import com.fs.api.football.domain.*;
import com.fs.common.enums.URL;
import com.fs.common.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class FixtureService {

    private final WebClient webClient;
    private final CountryRepository countryRepository;
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;

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
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "81fe2c5be0msh192ce0f95db9856p1c396ejsn2af5c49a0a78")
                .retrieve()
                .bodyToMono(Map.class)
                .subscribe(data -> {

                }, error -> {

                });
    }

}
