package com.fs.api.football.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StandingDto extends ApiResponse {

    private List<Response> response;

    @Data
    public static class Response {

        private League league;
        private List<Standing> standings;

        @Data
        public static class League {
            private long id;
            private String name;
            private String country;
            private String logo;
            private String flag;
            private int season;
        }

        @Data
        public static class Standing {
            private int rank;
            private Team team;
            private int points;
            private int goalsDiff;
            private String group;
            private String form;
            private String status;
            private String description;
            private Game all;
            private Game home;
            private Game away;
            private OffsetDateTime update;

            @Data
            public static class Team {
                private int id;
                private String name;
                private String logo;

            }

            @Data
            public static class Game {
                private int played;
                private int win;
                private int draw;
                private int lose;
                private Goal goals;

                @Data
                public static class Goal {
                    @JsonAlias(value = "for")
                    private int _for;
                    private int against;
                }
            }

        }

    }


}
