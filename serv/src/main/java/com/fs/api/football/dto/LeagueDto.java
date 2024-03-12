package com.fs.api.football.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LeagueDto extends ApiResponse {

    List<LeagueResponse> response;

    @Data
    public static class LeagueResponse {
        private League league;
        private Country country;
        private List<Season> seasons;
    }

    @Data
    public static class League {
        private int id;
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
//        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private String start;
//        @DateTimeFormat(pattern = "yyyy-MM-dd")
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

}
