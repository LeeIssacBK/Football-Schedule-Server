package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends BaseRepository<Country> {

    Country getByName(String name);
    Optional<Country> findByCodeAndName(String code, String name);
}
