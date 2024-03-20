package com.fs.configs.security.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends BaseRepository<Code> {

    Optional<Code> findByK(String key);

}
