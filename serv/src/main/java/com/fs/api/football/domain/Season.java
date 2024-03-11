package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Season extends BaseDomain {

    private int year;

}
