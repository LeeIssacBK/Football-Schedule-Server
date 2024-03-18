package com.fs.api.football.dto;

import lombok.Data;

import java.time.LocalDate;

public class SeasonDto {

    @Data
    public static class AppResponse {
        private int year;
        private LocalDate start;
        private LocalDate end;
        private boolean current;
    }

}
