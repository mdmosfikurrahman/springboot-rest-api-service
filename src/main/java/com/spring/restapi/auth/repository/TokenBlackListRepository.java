package com.spring.restapi.auth.repository;

import com.spring.restapi.auth.model.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {
    boolean existsByTokenAndInvalidatedAtIsNotNull(String token);
    TokenBlackList findByToken(String token);

    @Modifying
    @Query("UPDATE TokenBlackList t SET t.invalidatedAt = :invalidatedAt WHERE t.token = :token AND t.userId = :userId AND t.invalidatedAt IS NULL")
    void invalidateToken(@Param("token") String token, @Param("userId") Long userId, @Param("invalidatedAt") LocalDateTime invalidatedAt);
}
