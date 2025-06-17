package com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.repository;

import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.TweetReaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetReactionRepository extends JpaRepository<TweetReaction, Long> {
    Optional<TweetReaction> findByTweetIdAndUserId(Long tweetId, Long userId);
    void deleteByTweetIdAndUserId(Long tweetId, Long userId);
}
