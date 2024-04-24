package com.fs.api.football.service;

import com.fs.api.football.domain.Country;
import com.fs.api.football.domain.CountryRepository;
import com.fs.api.football.dto.CountryDto;
import com.fs.api.football.dto.CountryDtoMapper;
import com.fs.api.football.util.ApiProvider;
import com.fs.common.enums.URL;
import com.fs.common.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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
                .headers(ApiProvider.getHeader())
                .retrieve()
                .bodyToMono(CountryDto.class)
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

    public List<CountryDto.AppResponse> get() {
        return CountryDtoMapper.INSTANCE.toAppResponse(countryRepository.findAllByFlagIsNotNullAndKrNameIsNotNullOrderByKrNameAsc());
    }

}
