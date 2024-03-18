package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends BaseRepository<Team> {

    Optional<Team> findByApiId(long apiId);

    Optional<List<Team>> findAllByLeagueAndSeason(League league, Season season);
}
