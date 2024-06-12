package com.fs.api.alert.service;

import com.fs.api.alert.domain.Alert;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fs.api.alert.dto.AlertDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class FirebaseService {

    private final FirebaseMessaging firebaseMessaging;

    public List<Long> send(List<AlertDto.Message> alerts) throws FirebaseMessagingException {
        List<Long> sendIds = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        alerts.forEach(alert -> {

            messages.add(
                    Message.builder()
                            .setNotification(
                                    Notification.builder()
                                            .build())
                            .setToken(alert.getFcmToken())
                            .build());
        });

        // This registration token comes from the client FCM SDKs.
        String registrationToken = "eLGrQlXNThifWAyeMY5mZ7:APA91bFCgKQaND9uBMvD5YG13lNIGKBRuWFrIZTkgi1oOkWOm4pceOHWKBaKZQKV3l8mMhcukTYX58HRUYvXulmd_dLW3GgVuJWz6iW79lFstnlDS7v2cSRJ6GI569PZWuVgvZQsmPed";

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("message title")
                        .setBody("message body")
                        .build())
                .setToken(registrationToken)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = firebaseMessaging.send(message);
        // Response is a message ID string.
        log.info("Successfully sent message: " + response);

        return sendIds;
    }


}
