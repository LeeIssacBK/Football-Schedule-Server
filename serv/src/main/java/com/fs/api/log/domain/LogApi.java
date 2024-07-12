package com.fs.api.log.domain;

import com.fs.common.enums.LogApiType;
import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class LogApi extends BaseDomain {

    @Enumerated(value = EnumType.STRING)
    private LogApiType type;

    @Column(columnDefinition = "TEXT")
    private String requestData;

    @Column(columnDefinition = "TEXT")
    private String responseData;

}
