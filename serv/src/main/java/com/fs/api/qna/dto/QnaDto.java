package com.fs.api.qna.dto;

import lombok.Data;

public class QnaDto {

    @Data
    public static class Request {

    }

    @Data
    public static class Response {
        private long id;
        private String title;
        private String content;
    }

}
