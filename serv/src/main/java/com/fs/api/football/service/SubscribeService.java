package com.fs.api.football.service;

import com.fs.api.football.domain.*;
import com.fs.api.football.dto.SubscribeDto;
import com.fs.api.football.dto.SubscribeDtoMapper;
import com.fs.api.user.dto.UserDto;
import com.fs.api.user.domain.UserRepository;
import com.fs.common.enums.SubscribeType;
import com.fs.common.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subscribe(UserDto.Simple user, SubscribeDto.Request request) {
        if (SubscribeType.TEAM.equals(request.getType())) {
            Team team = teamRepository.findByApiId(request.getApiId()).orElseThrow(() -> new NotFoundException("team"));
            subscribeRepository.findByTeamAndUserUserId(team, user.getUserId()).ifPresentOrElse(subscribe -> {
                subscribe.setDelete(false);
                }, () -> {
                subscribeRepository.save(Subscribe.builder()
                                    .type(request.getType())
                                    .team(teamRepository.findByApiId(request.getApiId()).orElseThrow(() -> new NotFoundException("team")))
                                    .user(userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NotFoundException("user")))
                                    .build());
                });
        }
        if (SubscribeType.PLAYER.equals(request.getType())) {
            Player player = playerRepository.findByApiId(request.getApiId()).orElseThrow(() -> new NotFoundException("player"));
            subscribeRepository.findByPlayerAndUserUserId(player, user.getUserId()).ifPresentOrElse(subscribe -> {
                subscribe.setDelete(false);
            }, () -> {
                subscribeRepository.save(Subscribe.builder()
                        .type(request.getType())
                        .player(playerRepository.findByApiId(request.getApiId()).orElseThrow(() -> new NotFoundException("player")))
                        .user(userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NotFoundException("user")))
                        .build());
            });
        }
    }

    @Transactional
    public ResponseEntity<?> unSubscribe(UserDto.Simple user, SubscribeDto.Request request) {
        if (SubscribeType.TEAM.equals(request.getType())) {
            Team team = teamRepository.findByApiId(request.getApiId()).orElseThrow(() -> new NotFoundException("team"));
            subscribeRepository.findByTeamAndUserUserId(team, user.getUserId())
                    .ifPresent(subscribe -> subscribe.setDelete(true));
        }
        if (SubscribeType.PLAYER.equals(request.getType())) {
            Player player = playerRepository.findByApiId(request.getApiId()).orElseThrow(() -> new NotFoundException("player"));
            subscribeRepository.findByPlayerAndUserUserId(player, user.getUserId())
                    .ifPresent(subscribe -> subscribe.setDelete(true));
        }
        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = true)
    public List<SubscribeDto.Response> get(UserDto.Simple user, SubscribeType type) {
        return SubscribeDtoMapper.INSTANCE.toResponses(
                subscribeRepository.findAllByTypeAndUserUserIdAndIsDeleteFalse(type, user.getUserId())
                        .orElseThrow(() -> new NotFoundException("subscribe")));
    }

}
