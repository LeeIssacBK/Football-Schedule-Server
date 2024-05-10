package com.fs.api.alert.service;

import com.fs.api.alert.domain.Alert;
import com.fs.api.alert.domain.AlertRepository;
import com.fs.api.football.domain.FixtureRepository;
import com.fs.api.user.domain.User;
import com.fs.api.user.dto.UserDto;
import com.fs.api.user.repository.UserRepository;
import com.fs.common.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlertService {

    private final UserRepository userRepository;
    private final FixtureRepository fixtureRepository;
    private final AlertRepository alertRepository;


    public ResponseEntity saveAlert(UserDto.Simple user, long fixtureId) {
        alertRepository.save(Alert.builder()
                        .to(userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NotFoundException("user")))
                        .fixture(fixtureRepository.findByApiId(fixtureId).orElseThrow(() -> new NotFoundException("fixture")))
                .build());
        return ResponseEntity.ok().build();
    }


}
