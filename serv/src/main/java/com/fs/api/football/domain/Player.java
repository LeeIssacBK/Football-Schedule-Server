package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Player extends BaseDomain {

    @ManyToOne
    private Team team;
    @Column(unique = true)
    private long apiId;
    private String name;
    private String firstname;
    private String lastname;
    private Integer age;
    private LocalDate birth;
    private String place;
    private String country;
    private String nationality;
    private String height;
    private String weight;
    private Boolean injured;
    private String photo;

}
