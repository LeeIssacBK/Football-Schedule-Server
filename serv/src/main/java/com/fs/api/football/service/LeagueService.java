package com.fs.api.football.service;

import com.fs.api.football.domain.*;
import com.fs.api.football.dto.LeagueDto;
import com.fs.api.football.dto.LeagueDtoMapper;
import com.fs.api.football.util.ApiProvider;
import com.fs.api.log.domain.LogApi;
import com.fs.api.log.domain.LogApiRepository;
import com.fs.common.enums.LogApiType;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.fs.api.football.domain.QFixture.fixture;
import static com.fs.api.football.domain.QLeague.league;

@Slf4j
@RequiredArgsConstructor
@Service
public class LeagueService {

    private final WebClient webClient;
    private final CountryRepository countryRepository;
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final JPAQueryFactory queryFactory;
    private final LogApiRepository logApiRepository;

    @Transactional
    public void update() {
        UriComponents url = UriComponentsBuilder.fromUriString(URL.FOOTBALL_API.getValue())
                .path("/leagues")
                .build();
        webClient.get()
                .uri(url.toUri())
                .headers(ApiProvider.getHeader())
                .retrieve()
                .bodyToMono(LeagueDto.class)
                .subscribe(data -> {
                    logApiRepository.save(
                            LogApi.builder()
                                    .type(LogApiType.LEAGUE)
                                    .requestData(url.toUriString())
                                    .responseData(data.getResponse().toString())
                                    .build()
                    );
                    data.getResponse().forEach(response -> {
                        try {
                            LeagueDto.League leagueResponse = response.getLeague();
                            LeagueDto.Country countryResponse = response.getCountry();
                            List<LeagueDto.Season> seasonsResponse = response.getSeasons();
                            League league = leagueRepository.findByApiId(leagueResponse.getId())
                                    .or(() -> Optional.of(leagueRepository.save(League.builder()
                                            .apiId(leagueResponse.getId())
                                            .name(leagueResponse.getName())
                                            .type(leagueResponse.getType().equals("Cup") ? League.Type.CUP : League.Type.LEAGUE)
                                            .logo(leagueResponse.getLogo())
                                            .country(countryRepository.findByCodeAndName(countryResponse.getCode(), countryResponse.getName())
                                                    .orElse(countryRepository.getByName("World")))
                                            .build()))).get();
                            seasonsResponse.forEach(season -> {
                                seasonRepository.findByLeagueAndCurrentIsTrue(league)
                                        .ifPresentOrElse(entity -> {
                                            entity.setYear(season.getYear());
                                            entity.setStart(LocalDate.parse(season.getStart(), DateTimeFormatter.ISO_DATE));
                                            entity.setEnd(LocalDate.parse(season.getEnd(), DateTimeFormatter.ISO_DATE));
                                            entity.setCurrent(season.isCurrent());
                                            seasonRepository.save(entity);
                                        }, () -> {
                                            seasonRepository.save(Season.builder()
                                                    .league(league)
                                                    .year(season.getYear())
                                                    .start(LocalDate.parse(season.getStart(), DateTimeFormatter.ISO_DATE))
                                                    .end(LocalDate.parse(season.getEnd(), DateTimeFormatter.ISO_DATE))
                                                    .current(season.isCurrent())
                                                    .build());
                                        });
                            });
                        } catch (Exception e) {
                            log.error("error league ::: " + response.getLeague().getId() + ":" + response.getLeague().getName());
                            log.error(e.getMessage());
                        }
                    });
                    leagueRepository.updateSeasons();
                }, error -> {
                    throw new BadRequestException(error.getMessage());
                });
    }

    public List<LeagueDto.AppResponse> get(String countryCode) {
       return LeagueDtoMapper.INSTANCE.toAppResponse(leagueRepository.findAllByCountryCode(countryCode).orElseThrow(() -> new NotFoundException("league")));
    }

    public List<LeagueDto.AppResponse> getWithFixture(String countryCode) {
        return LeagueDtoMapper.INSTANCE.toAppResponse(
                queryFactory.select(league)
                        .from(fixture)
                        .where(fixture.league.country.code.eq(countryCode)
                                .and(fixture.league.type.eq(League.Type.LEAGUE)))
                        .orderBy(fixture.league.apiId.asc())
                        .fetch()
        );
    }

}
