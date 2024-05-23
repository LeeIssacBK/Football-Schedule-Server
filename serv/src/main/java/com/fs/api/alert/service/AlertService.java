package com.fs.api.alert.service;

import com.fs.api.alert.domain.Alert;
import com.fs.api.alert.domain.AlertRepository;
import com.fs.api.alert.dto.AlertDto;
import com.fs.api.alert.dto.AlertDtoMapper;
import com.fs.api.football.domain.FixtureRepository;
import com.fs.api.user.dto.UserDto;
import com.fs.api.user.repository.UserRepository;
import com.fs.common.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlertService {

    private final UserRepository userRepository;
    private final FixtureRepository fixtureRepository;
    private final AlertRepository alertRepository;


    @Transactional
    public void saveAlert(UserDto.Simple user, AlertDto.Request request) {
        request.typeCheck();
        alertRepository.save(Alert.builder()
                        .to(userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NotFoundException("user")))
                        .alertType(request.getAlertType())
                        .fixture(fixtureRepository.findByApiId(request.getFixtureId()).orElseThrow(() -> new NotFoundException("fixture")))
                .build());
    }

    @Transactional
    public void updateAlert(UserDto.Simple user, AlertDto.Request request) {
        request.typeCheck();
        alertRepository.findByToUserIdAndFixtureApiId(user.getUserId(), request.getFixtureId())
                .ifPresent(alert -> {
                    alert.setAlertType(request.getAlertType());
                    alertRepository.save(alert);
                });
    }

    @Transactional
    public void deleteAlert(UserDto.Simple user, AlertDto.Request request) {
        alertRepository.findByToUserIdAndFixtureApiId(user.getUserId(), request.getFixtureId())
                .ifPresent(alertRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<AlertDto.Response> getAlerts(UserDto.Simple user) {
        return AlertDtoMapper.INSTANCE.toResponses(
                alertRepository.findAllByToUserId(user.getUserId())
        );
    }

}
