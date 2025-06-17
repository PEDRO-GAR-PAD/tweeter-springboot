package com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.repository;

import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}
