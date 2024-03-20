package com.fs.api.football.dto;

import com.fs.common.enums.SubscribeType;
import lombok.Data;

public class SubscribeDto {

    @Data
    public static class Request {
        private SubscribeType type;
    }

    @Data
    public static class Response {

    }

}
