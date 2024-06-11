package com.fs.api.user.dto;

import lombok.Data;

public class DeviceDto {

    @Data
    public static class Request {
        private String platform;
        private String uuid;
        private String fcmToken;
    }

}
