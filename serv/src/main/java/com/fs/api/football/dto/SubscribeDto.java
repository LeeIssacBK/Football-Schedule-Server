package com.fs.api.football.dto;

import com.fs.common.enums.SubscribeType;
import lombok.Data;

public class SubscribeDto {

    @Data
    public static class Request {
        private SubscribeType type;
        private long apiId;
    }

    @Data
    public static class Response {
        private SubscribeType type;
        private LeagueDto.AppResponse league;
        private TeamDto.AppResponse team;
        private boolean isDelete;
    }

}
