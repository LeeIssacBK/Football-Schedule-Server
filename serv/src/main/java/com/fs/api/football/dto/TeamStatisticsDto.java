package com.fs.api.football.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class TeamStatisticsDto extends ApiResponse {

    private Response response;

    @Data
    public static class Response {
        private League league;
        private Team team;
        private String form;
        private Fixtures fixtures;
        private Goals goals;
        private Biggest biggest;
        private Score clean_sheet;
        private Score failed_to_score;
        private Penalty penalty;
        private List<LineUp> lineups;
        private Cards cards;

        @Data
        public static class League {
            private Integer id;
            private String name;
            private String country;
            private String logo;
            private String flag;
            private Integer season;
        }

        @Data
        public static class Team {
            private Integer id;
            private String name;
            private String logo;
        }

        @Data
        public static class Fixtures {
            private FixturesInteger played;
            private FixturesInteger wins;
            private FixturesInteger draws;
            private FixturesInteger loses;

            @Data
            public static class FixturesInteger {
                private Integer home;
                private Integer away;
                private Integer total;
            }
        }

        @Data
        public static class Goals {
            @JsonAlias(value = "for")
            private Goal _for;
            private Goal against;

            @Data
            public static class Goal {
                private GoalInteger total;
                private GoalString average;
                private Minute minute;

                @Data
                public static class GoalInteger {
                    private Integer home;
                    private Integer away;
                    private Integer total;
                }

                @Data
                public static class GoalString {
                    private String home;
                    private String away;
                    private String total;
                }
            }
        }

        @Data
        public static class StatisticsGameTime {
            private Integer total;
            private String percentage;
        }

        @Data
        public static class Minute {
            @JsonAlias(value = "0-15")
            private StatisticsGameTime _0_15;
            @JsonAlias(value = "16-30")
            private StatisticsGameTime _16_30;
            @JsonAlias(value = "31-45")
            private StatisticsGameTime _31_45;
            @JsonAlias(value = "46-60")
            private StatisticsGameTime _46_60;
            @JsonAlias(value = "61-75")
            private StatisticsGameTime _61_75;
            @JsonAlias(value = "76-90")
            private StatisticsGameTime _76_90;
            @JsonAlias(value = "91-105")
            private StatisticsGameTime _91_105;
            @JsonAlias(value = "106-120")
            private StatisticsGameTime _106_120;
            @JsonAlias(value = "")
            private StatisticsGameTime _note;
        }

        @Data
        public static class Biggest {
            private Streak streak;
            private Score wins;
            private Score loses;
            private Goals goals;

            @Data
            public static class Streak {
                private Integer wins;
                private Integer draws;
                private Integer loses;
            }
            @Data
            public static class Score {
                private String home;
                private String away;
            }

            @Data
            public static class Goals {
                @JsonAlias(value = "for")
                private Score _for;
                private Score against;

                @Data
                public static class Score {
                    private Integer home;
                    private Integer away;
                }
            }
        }

        @Data
        public static class Score {
            private Integer home;
            private Integer away;
            private Integer total;
        }

        @Data
        public static class Penalty {
            private _Data scored;
            private _Data missed;
            private Integer total;

            @Data
            public static class _Data {
                private Integer total;
                private String percentage;
            }
        }

        @Data
        public static class LineUp {
            private String formation;
            private Integer played;
        }

        @Data
        public static class Cards {
            private Minute yellow;
            private Minute red;
        }
    }

    @Data
    public static class AppResponse {

    }

}
