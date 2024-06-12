package com.fs.api.alert.service;

import com.fs.api.alert.dto.AlertDto;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class FirebaseService {

    private final FirebaseMessaging firebaseMessaging;

    public void send(List<AlertDto.Message> alerts) throws FirebaseMessagingException {
        List<Message> messages = new ArrayList<>();
        alerts.forEach(alert -> {
            messages.add(
                    Message.builder()
                            .setNotification(
                                    Notification.builder()
//                                            .setImage(alert.getLeagueLogo())
                                            .setTitle("경기시작 알림")
                                            .setBody(generateMessage(alert))
                                            .build())
                            .setToken(alert.getFcmToken())
                            .build());
        });
        BatchResponse response = firebaseMessaging.sendAll(messages);

        log.warn(
                String.format("[FIREBASE MESSAGE SEND]\nsuccess count : %d || failure count : %d",
                        response.getSuccessCount(),
                        response.getFailureCount())
        );
    }

    private String generateMessage(AlertDto.Message alert) {
        return String.format("%s vs %s 경기가 %s 뒤에 시작됩니다.",
                alert.getHomeKrName() != null ? alert.getHomeKrName() : alert.getHomeName(),
                alert.getAwayKrName() != null ? alert.getAwayKrName() : alert.getAwayName(),
                alert.getAlertType().getKrDes());
    }

}
