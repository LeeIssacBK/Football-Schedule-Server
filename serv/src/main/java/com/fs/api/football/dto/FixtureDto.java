package com.fs.api.football.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class FixtureDto extends ApiResponse {



    @Data
    public static class Fixture {
        private long id;
        private String referee;
        private String timezone;
        private LocalDateTime date;
        private String timestamp;

    }

    @Data
    public static class Status {
        @JsonAlias(value = "long")
        private String _long;
        @JsonAlias(value = "short")
        private String _short;
        private int elapsed;
    }


}
