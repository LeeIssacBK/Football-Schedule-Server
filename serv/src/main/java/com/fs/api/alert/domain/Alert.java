package com.fs.api.alert.domain;

import com.fs.api.football.domain.Fixture;
import com.fs.api.user.domain.User;
import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Alert extends BaseDomain {

    public enum AlertType {
        BEFORE_30MINUTES,
        BEFORE_1HOURS,
        BEFORE_3HOURS,
        BEFORE_6HOURS,
        BEFORE_1DAYS
    }

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private Fixture fixture;

    @ManyToOne(fetch = FetchType.LAZY)
    private User to;

    @ColumnDefault("false")
    private boolean isSend;

    @Enumerated(EnumType.STRING)
    private AlertType alertType = AlertType.BEFORE_30MINUTES;

}
