package com.fs.api.support.dto;

import lombok.Data;

public class SupportDto {

    @Data
    public static class Request {
        private String type;
        private String title;
        private String content;
    }

}
