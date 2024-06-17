package com.fs.api.user.dto;

import com.fs.api.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MyInfoDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Response {
        private User.SocialType socialType;
        private int subscribeTeamCount;
        private int totalAlertsCount;
    }

}
