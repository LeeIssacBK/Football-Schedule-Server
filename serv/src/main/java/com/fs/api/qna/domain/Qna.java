package com.fs.api.qna.domain;

import com.fs.api.user.domain.User;
import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Qna extends BaseDomain {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("false")
    private boolean isDelete;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

}
