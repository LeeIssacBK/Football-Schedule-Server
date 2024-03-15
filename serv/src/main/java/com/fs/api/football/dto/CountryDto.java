package com.fs.api.football.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CountryDto extends ApiResponse {

    private List<Response> response;

    @Data
    public static class Response {
        private String code;
        private String name;
        private String flag;
    }

    @Data
    public static class AppResponse {
        private String code;
        private String name;
        private String krName;
        private String flag;
    }

}
