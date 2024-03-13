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

}
