package com.fs.api.football.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fs.api.football.domain.League.Type;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LeagueDto extends ApiResponse {

    private List<Response> response;

    @Data
    public static class Response {
        private League league;
        private Country country;
        private List<Season> seasons;
    }

    @Data
    public static class League {
        private long id;
        private String name;
        private String type;
        private String logo;
    }

    @Data
    public static class Country {
        private String name;
        private String code;
        private String flag;
    }

    @Data
    public static class Season {
        private int year;
        private String start;
        private String end;
        private boolean current;
        private Coverage coverage;
    }

    @Data
    public static class Coverage {
        private Fixture fixtures;
        private boolean standings;
        private boolean players;
        private boolean top_scorers;
        private boolean top_assists;
        private boolean top_cards;
        private boolean injuries;
        private boolean predictions;
        private boolean odds;
    }

    @Data
    public static class Fixture {
        private boolean events;
        private boolean lineups;
        private boolean statistics_fixtures;
        private boolean statistics_players;
    }

    @Data
    public static class AppResponse {

        private long apiId;
        private String name;
        private String logo;
        private Type type;

    }

}
