package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamStatisticsRepository extends BaseRepository<TeamStatistics> {

    Optional<TeamStatistics> findByTeam(Team team);

}
