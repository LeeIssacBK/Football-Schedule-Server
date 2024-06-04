package com.fs.api.football.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StandingDto extends ApiResponse {

    private List<Response> response;

    @Data
    public static class Response {

        private League league;

        @Data
        public static class League {
            private Long id;
            private String name;
            private String country;
            private String logo;
            private String flag;
            private Integer season;
            private List<Standing> standings;
        }

        @Data
        public static class Standing {
            @JsonAlias("rank")
            private Integer _rank;
            private Team team;
            private Integer points;
            private Integer goalsDiff;
            private String _group;
            private String form;
            @JsonAlias("status")
            private String _status;
            private String description;
            @JsonAlias("all")
            private Game _all;
            private Game home;
            private Game away;

            @Data
            public static class Team {
                private Integer id;
                private String name;
                private String logo;
            }

            @Data
            public static class Game {
                private Integer played;
                private Integer win;
                private Integer draw;
                private Integer lose;
                private Goal goals;

                @Data
                public static class Goal {
                    @JsonAlias(value = "for")
                    private Integer _for;
                    private Integer against;
                }
            }

        }

    }


}
