package com.fs.api.user.repository;

import com.fs.api.user.domain.User;
import com.fs.api.user.domain.User.SocialType;
import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByUserId(String userId);
    long countByUserId(String userId);

    Optional<User> findByUserIdAndSocialType(String id, SocialType socialType);

}
