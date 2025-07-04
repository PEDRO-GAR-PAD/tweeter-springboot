package com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.repository;

import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
