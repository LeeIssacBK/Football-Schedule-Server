package com.fs.api.football.domain;

import com.fs.common.enums.SubscribeType;
import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends BaseRepository<Subscribe> {

    Optional<Subscribe> findByTeamAndUserUserId(Team team, String userId);
    Optional<Subscribe> findByPlayerAndUserUserId(Player player, String userId);
    Optional<List<Subscribe>> findAllByTypeAndUserUserId(SubscribeType type, String userId);

}
