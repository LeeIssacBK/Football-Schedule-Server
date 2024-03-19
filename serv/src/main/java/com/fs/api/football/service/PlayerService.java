package com.fs.api.football.service;

import com.fs.api.football.domain.Player;
import com.fs.api.football.domain.PlayerRepository;
import com.fs.api.football.domain.Team;
import com.fs.api.football.domain.TeamRepository;
import com.fs.api.football.dto.PlayerDto;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayerService {

    private final WebClient webClient;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    @Transactional
    public void update(long teamId) {
        Team team = teamRepository.findByApiId(teamId).orElseThrow(() -> new NotFoundException("team"));
        UriComponents url = UriComponentsBuilder.fromUriString(URL.FOOTBALL_API.getValue())
                .path("/players")
                .queryParam("team", teamId)
                .queryParam("season", team.getSeason().getYear())
                .build();
        webClient.get()
                .uri(url.toUri())
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "81fe2c5be0msh192ce0f95db9856p1c396ejsn2af5c49a0a78")
                .retrieve()
                .bodyToMono(PlayerDto.class)
                .subscribe(data -> {
                    data.getResponse().forEach(response -> {
                        try {
                            PlayerDto.Player playerResponse = response.getPlayer();
                            PlayerDto.Birth playerBirthResponse = response.getPlayer().getBirth();
                            playerRepository.findByApiId(playerResponse.getId())
                                    .ifPresentOrElse(player -> {
                                        //update..
                                    }, () -> {
                                        playerRepository.save(Player.builder()
                                                .team(team)
                                                .apiId(playerResponse.getId())
                                                .name(playerResponse.getName())
                                                .firstname(playerResponse.getFirstname())
                                                .lastname(playerResponse.getFirstname())
                                                .age(playerResponse.getAge())
                                                .birth(LocalDate.parse(playerBirthResponse.getDate(), DateTimeFormatter.ISO_DATE))
                                                .place(playerBirthResponse.getPlace())
                                                .country(playerBirthResponse.getCountry())
                                                .nationality(playerResponse.getNationality())
                                                .height(playerResponse.getHeight())
                                                .weight(playerResponse.getWeight())
                                                .injured(playerResponse.getInjured())
                                                .photo(playerResponse.getPhoto()).build());
                                    }
                            );
                        } catch (Exception e) {
                            log.error("error player ::: " + response.getPlayer().getId() + ":" + response.getPlayer().getName());
                            log.error(e.getMessage());
                        }
                    });
                }, error -> {
                    throw new BadRequestException(error.getMessage());
                });
    }
}
