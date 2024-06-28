package com.fs.api.user.domain;

import com.fs.api.user.domain.User.SocialType;
import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByUserId(String userId);

    long countByUserId(String userId);

    Optional<User> findByUserIdAndSocialType(String id, SocialType socialType);

    @Modifying
    @Query(value = "update user " +
            "set status = 'WITHDRAWAL' " +
            "where status = 'PENDING' " +
            "and withdraw_at < date_sub(now(), interval 3 day) "
            , nativeQuery = true)
    void updateWithdrawUser();

    List<User> findAllByStatus(User.Status status);

}
