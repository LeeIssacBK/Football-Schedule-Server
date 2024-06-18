package com.fs.api.support.domain;

import com.fs.api.user.domain.User;
import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Support extends BaseDomain {

    private String type;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

}
