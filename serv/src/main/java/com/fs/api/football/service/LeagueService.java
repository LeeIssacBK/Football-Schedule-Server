package com.fs.api.football.service;

import com.fs.api.football.domain.*;
import com.fs.api.football.dto.LeagueDto;
import com.fs.common.enums.URL;
import com.fs.common.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LeagueService {

    private final WebClient webClient;
    private final CountryRepository countryRepository;
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;

    @Transactional
    public void update() {
        List<String> errorLeagues = new ArrayList<>();
        UriComponents url = UriComponentsBuilder.fromUriString(URL.FOOTBALL_API.getValue())
                .path("/leagues")
                .build();
        webClient.get()
                .uri(url.toUri())
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "81fe2c5be0msh192ce0f95db9856p1c396ejsn2af5c49a0a78")
                .retrieve()
                .bodyToMono(LeagueDto.class)
                .subscribe(data -> {
                    data.getResponse().forEach(response -> {
                        try {
                            LeagueDto.League leagueResponse = response.getLeague();
                            LeagueDto.Country countryResponse = response.getCountry();
                            List<LeagueDto.Season> seasonsResponse = response.getSeasons();
                            League league = leagueRepository.findByApiId(response.getLeague().getId())
                                    .or(() -> Optional.of(leagueRepository.save(League.builder()
                                            .apiId(leagueResponse.getId())
                                            .name(leagueResponse.getName())
                                            .type(leagueResponse.getType().equals("Cup") ? League.Type.CUP : League.Type.LEAGUE)
                                            .logo(leagueResponse.getLogo())
                                            .country(countryRepository.findByCode(countryResponse.getCode())
                                                    .orElse(countryRepository.getByName("World")))
                                            .build()))).get();
                            seasonRepository.deleteAllByLeague(league);
                            seasonsResponse.forEach(season -> {
                                seasonRepository.save(Season.builder()
                                        .league(league)
                                        .year(season.getYear())
                                        .start(LocalDate.parse(season.getStart(), DateTimeFormatter.ISO_DATE))
                                        .end(LocalDate.parse(season.getEnd(), DateTimeFormatter.ISO_DATE))
                                        .current(season.isCurrent())
                                        .build());
                            });
                        } catch (Exception e) {
                            errorLeagues.add(response.getLeague().getName());
                        }
                    });
                }, error -> {
                    throw new BadRequestException(error.getMessage());
                });
        log.error("error leagues ::: " + errorLeagues.toString());
    }


}
