package com.fs.api.football.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CountryDto extends ApiResponse {

    private List<CountryResponse> response;

    @Data
    public static class CountryResponse {
        private String code;
        private String name;
        private String flag;
    }

}
