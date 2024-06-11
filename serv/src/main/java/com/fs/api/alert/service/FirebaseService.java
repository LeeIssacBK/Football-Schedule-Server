package com.fs.api.alert.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class FirebaseService {

    private final FirebaseMessaging firebaseMessaging;

    public void send() throws FirebaseMessagingException {
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
        System.out.println("Successfully sent message: " + response);
    }


}
