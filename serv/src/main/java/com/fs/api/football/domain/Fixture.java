package com.fs.api.football.domain;

import com.fs.api.football.dto.FixtureDto;
import com.fs.common.exceptions.NotFoundException;
import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Fixture extends BaseDomain {

    public enum Status {
        NS, TBD, //NOT STARTED
        FT, AET, PEN;  //FINISHED
        public static Status parse(String str) {
            return switch (str) {
                case "NS" -> Status.NS;
                case "TBD" -> Status.TBD;
                case "FT" -> Status.FT;
                case "AET" -> Status.AET;
                case "PEN" -> Status.PEN;
                default -> throw new NotFoundException("fixture status");
            };
        }
    }

    public enum MatchResult {
        HOME_WIN, AWAY_WIN, DRAW;
        public static MatchResult parse(FixtureDto.Teams teams) {
            Boolean isHomeWin = teams.getHome().getWinner();
            Boolean isAwayWin = teams.getAway().getWinner();
            if (isHomeWin == null && isAwayWin == null) {
                return MatchResult.DRAW;
            }
            if (isHomeWin) {
                return MatchResult.HOME_WIN;
            }
            if (isAwayWin) {
                return MatchResult.AWAY_WIN;
            }
            return null;
        }
    }

    @Column(unique = true)
    private long apiId;
    private String round;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private String referee;
    private LocalDateTime date;
    @ManyToOne
    private League league;
    @ManyToOne
    private Team home;
    @ManyToOne
    private Team away;
    private Integer homeGoal;
    private Integer awayGoal;
    @Enumerated(value = EnumType.STRING)
    private MatchResult matchResult;

}
