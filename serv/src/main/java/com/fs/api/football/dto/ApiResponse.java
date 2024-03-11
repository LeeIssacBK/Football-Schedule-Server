package com.fs.api.football.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {

    private String get;
    private List<String> parameters;
    private List<String> errors;
    private int results;
    private Paging paging;

    @Data
    public static class Paging {
        private int current;
        private int total;
    }

}
