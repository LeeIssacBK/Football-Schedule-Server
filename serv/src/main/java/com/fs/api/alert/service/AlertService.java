package com.fs.api.alert.service;

import com.fs.api.alert.domain.Alert;
import com.fs.api.alert.domain.AlertRepository;
import com.fs.api.alert.dto.AlertDto;
import com.fs.api.alert.dto.AlertDtoMapper;
import com.fs.api.football.domain.Fixture;
import com.fs.api.football.domain.FixtureRepository;
import com.fs.api.user.domain.UserRepository;
import com.fs.api.user.dto.UserDto;
import com.fs.common.exceptions.NotFoundException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.fs.api.alert.domain.QAlert.alert;
import static com.fs.api.user.domain.QDevice.device;

@Transactional
@RequiredArgsConstructor
@Service
public class AlertService {

    private final UserRepository userRepository;
    private final FixtureRepository fixtureRepository;
    private final AlertRepository alertRepository;
    private final JPAQueryFactory queryFactory;

    public void saveAlert(UserDto.Simple user, AlertDto.Request request) {
        request.typeCheck();
        Alert.AlertType alertType = request.getAlertType();
        Fixture fixture = fixtureRepository.findByApiId(request.getFixtureId()).orElseThrow(() -> new NotFoundException("fixture"));
        alertRepository.save(Alert.builder()
                        .to(userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NotFoundException("user")))
                        .alertType(alertType)
                        .fixture(fixture)
                        .sendTime(generateSendTime(fixture.getDate(), alertType))
                .build());
    }

    public void updateAlert(UserDto.Simple user, AlertDto.Request request) {
        request.typeCheck();
        alertRepository.findByToUserIdAndFixtureApiId(user.getUserId(), request.getFixtureId())
                .ifPresent(alert -> {
                    Alert.AlertType alertType = request.getAlertType();
                    alert.setAlertType(alertType);
                    alert.setSendTime(generateSendTime(alert.getFixture().getDate(), alertType));
                    alertRepository.save(alert);
                });
    }

    public void deleteAlert(UserDto.Simple user, AlertDto.Request request) {
        alertRepository.findByToUserIdAndFixtureApiId(user.getUserId(), request.getFixtureId())
                .ifPresent(alertRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<AlertDto.Response> getAlerts(UserDto.Simple user) {
        return AlertDtoMapper.INSTANCE.toResponses(
                alertRepository.findAllByToUserIdOrderByFixtureDate(user.getUserId())
        );
    }

    public List<AlertDto.Message> getAlertMessages() {
        List<AlertDto.Message> alerts = queryFactory
                .select(Projections.constructor(
                        AlertDto.Message.class,
                        alert.id.as("alertId"),
                        device.uuid.as("uuid"),
                        device.platform.as("platform"),
                        device.fcmToken.as("fcmToken"),
                        alert.alertType.as("alertType"),
                        alert.fixture.date.as("date"),
                        alert.fixture.home.krName.as("home"),
                        alert.fixture.away.krName.as("away"))
                )
                .from(alert)
                .innerJoin(device)
                .on(alert.to.eq(device.user))
                .where(alert.isSend.isFalse()
                        .and(alert.sendTime.isNotNull())
                        .and(alert.sendTime.eq(LocalDateTime.now()))
                )
                .orderBy(alert.fixture.date.asc())
                .fetch();
        alertRepository.updateAllByIsSendTrue(alerts.stream().map(AlertDto.Message::getAlertId).toList());
        return alerts;
    }

    private LocalDateTime generateSendTime(LocalDateTime fixtureDateTime, Alert.AlertType alertType) {
        fixtureDateTime = fixtureDateTime.plusHours(9);
        if (Alert.AlertType.BEFORE_30MINUTES.equals(alertType)) {
            return fixtureDateTime.minusMinutes(30);
        }
        if (Alert.AlertType.BEFORE_1HOURS.equals(alertType)) {
            return fixtureDateTime.minusHours(1);

        }
        if (Alert.AlertType.BEFORE_3HOURS.equals(alertType)) {
            return fixtureDateTime.minusHours(3);

        }
        if (Alert.AlertType.BEFORE_6HOURS.equals(alertType)) {
            return fixtureDateTime.minusHours(6);

        }
        if (Alert.AlertType.BEFORE_1DAYS.equals(alertType)) {
            return fixtureDateTime.minusDays(1);
        }
        throw new NotFoundException("unsupported alert type");
    }

}
