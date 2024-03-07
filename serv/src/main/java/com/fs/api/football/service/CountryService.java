package com.fs.api.football.service;

import com.fs.api.football.domain.Country;
import com.fs.api.football.domain.CountryRepository;
import com.fs.api.football.dto.CountryDto;
import com.fs.common.enums.URL;
import com.fs.common.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final WebClient webClient;
    private final CountryRepository countryRepository;

    @Transactional
    public void update() {
        UriComponents url = UriComponentsBuilder.fromUriString(URL.FOOTBALL_API.getValue())
                .path("/countries")
                .build();
        webClient.get()
                .uri(url.toUri())
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "81fe2c5be0msh192ce0f95db9856p1c396ejsn2af5c49a0a78")
                .retrieve()
                .bodyToMono(CountryDto.Response.class)
                .subscribe(data -> {
                    countryRepository.deleteAll();
                    data.getResponse().forEach(country -> {
                        countryRepository.save(Country.builder()
                                        .code(country.getCode())
                                        .name(country.getName())
                                        .flag(country.getFlag()).build());
                    });
                }, error -> {
                    throw new BadRequestException(error.getMessage());
                });
    }

}
