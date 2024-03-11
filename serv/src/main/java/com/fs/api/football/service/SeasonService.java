package com.fs.api.football.service;

import com.fs.api.football.domain.Season;
import com.fs.api.football.domain.SeasonRepository;
import com.fs.api.football.dto.SeasonDto;
import com.fs.common.enums.URL;
import com.fs.common.exceptions.BadRequestException;
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
public class SeasonService {

    private final WebClient webClient;
    private final SeasonRepository seasonRepository;

    @Transactional
    public void update() {
        UriComponents url = UriComponentsBuilder.fromUriString(URL.FOOTBALL_API.getValue())
                .path("/leagues/seasons")
                .build();
        webClient.get()
                .uri(url.toUri())
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "81fe2c5be0msh192ce0f95db9856p1c396ejsn2af5c49a0a78")
                .retrieve()
                .bodyToMono(SeasonDto.class)
                .subscribe(data -> {
                    seasonRepository.deleteAll();
                    data.getResponse().forEach(year -> {
                        seasonRepository.save(Season.builder()
                                        .year(year)
                                .build());
                    });
                }, error -> {
                    throw new BadRequestException(error.getMessage());
                });
    }


}
