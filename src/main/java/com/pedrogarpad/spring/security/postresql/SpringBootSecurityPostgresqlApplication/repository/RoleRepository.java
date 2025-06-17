package com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.repository;

import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.ERole;
import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.Role;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
