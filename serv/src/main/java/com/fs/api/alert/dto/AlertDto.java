package com.fs.api.alert.dto;

import com.fs.api.alert.domain.Alert;
import com.fs.api.football.dto.FixtureDto;
import lombok.Data;

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


}
