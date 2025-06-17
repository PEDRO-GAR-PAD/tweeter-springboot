package com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.repository;

import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.CommentModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    // Trae todos los comentarios de un tweet
    List<CommentModel> findByTweetId(Long tweetId);

    // Borra un comentario sólo si es del usuario autenticado
    @Modifying
    @Transactional
    void deleteByIdAndUserId(Long commentId, Long userId);
}
