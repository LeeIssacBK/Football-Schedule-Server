package com.fs.api.alert.dto;

import com.fs.api.alert.domain.Alert;
import com.fs.api.football.dto.FixtureDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

public class AlertDto {

    @Data
    public static class Response {

        private FixtureDto.AppResponse fixture;

        private boolean isSend;

        private Alert.AlertType alertType;

    }

    @Data
    public static class Request {
        private long fixtureId;
        private Alert.AlertType alertType;
        public void typeCheck() {
            if (this.alertType == null) {
                this.alertType = Alert.AlertType.BEFORE_30MINUTES;
            }
        }
    }

    @Data
    @AllArgsConstructor
    public static class Message {
        private Long alertId;
        private String uuid;
        private String platform;
        private String fcmToken;
        private Alert.AlertType alertType;
        private LocalDateTime date;
        private String leagueLogo;
        private String homeName;
        private String homeKrName;
        private String awayName;
        private String awayKrName;
    }


}
