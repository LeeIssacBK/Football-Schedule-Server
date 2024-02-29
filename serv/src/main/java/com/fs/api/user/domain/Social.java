package com.fs.api.user.domain;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Social extends BaseDomain {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
