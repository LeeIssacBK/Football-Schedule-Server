package com.fs.api.football.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerDto extends ApiResponse {

    private List<PlayerDto.Response> response;

    @Data
    public static class Response {
        private Player player;
    }

    @Data
    public static class Player {
        private long id;
        private String name;
        private String firstname;
        private String lastname;
        private Integer age;
        private Birth birth;
        private String nationality;
        private String height;
        private String weight;
        private Boolean injured;
        private String photo;
    }

    @Data
    public static class Birth {
        private String date;
        private String place;
        private String country;
    }

}
