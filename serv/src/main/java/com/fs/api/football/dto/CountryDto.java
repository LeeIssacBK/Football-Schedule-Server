package com.fs.api.football.dto;

import lombok.Data;

import java.util.List;

public class CountryDto {

    @Data
    public static class Response {
        private String get;
        private List<String> parameters;
        private List<String> errors;
        private int results;
        private Paging paging;
        private List<ResponseData> response;
    }

    @Data
    public static class ResponseData {
        private String code;
        private String name;
        private String flag;
    }

    @Data
    public static class Paging {
        private int current;
        private int total;
    }


}
