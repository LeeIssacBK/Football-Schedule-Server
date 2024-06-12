package com.fs.api.alert.domain;

import com.fs.api.football.domain.Fixture;
import com.fs.api.user.domain.User;
import com.fs.common.exceptions.NotFoundException;
import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Alert extends BaseDomain {

    @Getter
    @AllArgsConstructor
    public enum AlertType {
        BEFORE_30MINUTES("30분"),
        BEFORE_1HOURS("1시간"),
        BEFORE_3HOURS("3시간"),
        BEFORE_6HOURS("6시간"),
        BEFORE_1DAYS("하루");

        private final String krDes;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Fixture fixture;

    @ManyToOne(fetch = FetchType.LAZY)
    private User to;

    @ColumnDefault("false")
    private boolean isSend;

    @Enumerated(EnumType.STRING)
    private AlertType alertType = AlertType.BEFORE_30MINUTES;

    private LocalDateTime sendTime;

}
