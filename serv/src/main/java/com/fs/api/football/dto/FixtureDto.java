package com.fs.api.football.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class FixtureDto extends ApiResponse {

    private List<Response> response;

    @Data
    public static class Response {
        private Fixture fixture;
        private League league;
        private Teams teams;
        private Goals goals;
    }

    @Data
    public static class Fixture {
        private long id;
        private String referee;
        private String timezone;
        private OffsetDateTime date;
        private String timestamp;
        private Status status;
    }

    @Data
    public static class Status {
        @JsonAlias(value = "long")
        private String _long;
        @JsonAlias(value = "short")
        private String _short;
        private int elapsed;
    }

    @Data
    public static class League {
        private long id;
        private String name;
        private String country;
        private String logo;
        private String flag;
        private int season;
        private String round;
    }

    @Data
    public static class Teams {
        private Team home;
        private Team away;
    }

    @Data
    public static class Team {
        private long id;
        private String name;
        private String logo;
        private Boolean winner;
    }

    @Data
    public static class Goals {
        private Integer home;
        private Integer away;
    }

    @Data
    public static class AppResponse {
        private long apiId;
        private String round;
        private com.fs.api.football.domain.Fixture.Status status;
        private String referee;
        private LocalDateTime date;
        private LeagueDto.AppResponse league;
        private TeamDto.AppResponse home;
        private TeamDto.AppResponse away;
        private Integer homeGoal;
        private Integer awayGoal;
        private com.fs.api.football.domain.Fixture.MatchResult matchResult;
    }


}
