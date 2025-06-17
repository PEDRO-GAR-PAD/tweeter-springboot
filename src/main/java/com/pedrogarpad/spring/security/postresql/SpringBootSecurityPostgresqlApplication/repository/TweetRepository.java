package com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.TweetModel;

@Repository
public interface TweetRepository extends JpaRepository<TweetModel, Long> {

}
