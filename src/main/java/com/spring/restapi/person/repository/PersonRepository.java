package com.spring.restapi.person.repository;

import com.spring.restapi.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByUserId(Long userId);
}
