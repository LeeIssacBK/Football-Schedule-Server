package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandingRepository extends BaseRepository<Standing> {

    Optional<Standing> findByTeamApiId(long teamId);

}
