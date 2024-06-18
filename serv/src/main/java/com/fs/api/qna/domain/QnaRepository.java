package com.fs.api.qna.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends BaseRepository<Qna> {
    List<Qna> findAllByDeleteIsFalse();

}
