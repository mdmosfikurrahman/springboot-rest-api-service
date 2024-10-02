package com.spring.restapi.repository;

import com.spring.restapi.model.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {
    boolean existsByToken(String token);
}
