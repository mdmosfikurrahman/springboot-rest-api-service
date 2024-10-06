package com.spring.restapi.auth.repository;

import com.spring.restapi.auth.model.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {
    boolean existsByTokenAndInvalidatedAtIsNotNull(String token);
    TokenBlackList findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE TokenBlackList t SET t.invalidatedAt = :invalidatedAt WHERE t.token = :token AND t.userId = :userId")
    void invalidateToken(String token, Long userId, LocalDateTime invalidatedAt);

    @Query("SELECT t FROM TokenBlackList t WHERE t.userId = :userId AND t.invalidatedAt IS NULL")
    TokenBlackList findByUserIdAndInvalidatedAtIsNull(@Param("userId") Long userId);

}
