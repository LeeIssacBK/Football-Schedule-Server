package com.fs.api.user.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Device extends BaseDomain {

    @Column(unique = true)
    private String uuid;

    private String platform;

    private String fcmToken;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
