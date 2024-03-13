package com.fs.api.football.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamDto extends ApiResponse {

    private List<Response> response;

    @Data
    public static class Response {
        private Team team;
        private Venue venue;
    }

    @Data
    public static class Team {
        private int id;
        private String name;
        private String code;
        private String country;
        private int founded;
        private boolean national;
        private String logo;
    }

    @Data
    public static class Venue {
        private int id;
        private String name;
        private String address;
        private String city;
        private int capacity;
        private String surface;
        private String image;
    }

}
