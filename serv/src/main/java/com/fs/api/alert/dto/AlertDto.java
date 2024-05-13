package com.fs.api.alert.dto;

import com.fs.api.alert.domain.Alert;
import com.fs.api.football.dto.FixtureDto;
import lombok.Data;

public class AlertDto {

    @Data
    public static class Response {

        private FixtureDto.AppResponse appResponse;

        private boolean isSend;

        private Alert.AlertType alertType;

    }


}
