package com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.controllers;

import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.Reaction;
import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.TweetModel;
import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.TweetReaction;
import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.models.User;
import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.payload.request.TweetReactionRequest;
import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.repository.ReactionRepository;
import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.repository.TweetReactionRepository;
import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.repository.TweetRepository;
import com.pedrogarpad.spring.security.postresql.SpringBootSecurityPostgresqlApplication.repository.UserRepository;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para crear, actualizar, borrar y listar reacciones.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reactions")
public class TweetReactionController {

    @Autowired
    private TweetReactionRepository tweetReactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private ReactionRepository reactionRepository;

    /**
     * Lista paginada de todas las reacciones
     */
    @GetMapping("/all")
    public Page<TweetReaction> getAllReactions(Pageable pageable) {
        return tweetReactionRepository.findAll(pageable);
    }

    /**
     * Lista de todos los tipos de reacción
     */
    @GetMapping("/types")
    public List<Reaction> getReactionTypes() {
        return reactionRepository.findAll();
    }

    /**
     * Crea una nueva reacción
     */
    /**
     * POST /api/reactions/create Si no existe, crea; si ya existe, simplemente
     * actualiza al nuevo tipo.
     */
    @PostMapping("/create")
    public TweetReaction createOrUpdateReaction(
            @Valid @RequestBody TweetReactionRequest req) {
        User user = getCurrentUser();
        TweetModel tweet = getValidTweet(req.getTweetId());
        Reaction reaction = getValidReaction(req.getReactionId());

        // 1) ¿Ya había reacción?
        Optional<TweetReaction> existingOpt
                = tweetReactionRepository.findByTweetIdAndUserId(tweet.getId(), user.getId());

        if (existingOpt.isPresent()) {
            // Actualizamos la reacción existente
            TweetReaction existing = existingOpt.get();
            existing.setReaction(reaction);
            return tweetReactionRepository.save(existing);
        } else {
            // Creamos una nueva
            TweetReaction tr = new TweetReaction();
            tr.setUser(user);
            tr.setTweet(tweet);
            tr.setReaction(reaction);
            return tweetReactionRepository.save(tr);
        }
    }

    /**
     * Actualiza la reacción existente del usuario a una nueva
     */
    @PutMapping("/update")
    public TweetReaction updateReaction(
            @Valid @RequestBody TweetReactionRequest req) {
        User user = getCurrentUser();
        TweetReaction existing = tweetReactionRepository
                .findByTweetIdAndUserId(req.getTweetId(), user.getId())
                .orElseThrow(() -> new RuntimeException("No reaction to update"));

        Reaction newReaction = getValidReaction(req.getReactionId());
        existing.setReaction(newReaction);
        return tweetReactionRepository.save(existing);
    }

    /**
     * Borra la reacción del usuario para un tweet dado
     */
    @DeleteMapping("/tweet/{tweetId}")
    public ResponseEntity<?> deleteReaction(@PathVariable Long tweetId) {
        User user = getCurrentUser();
        tweetReactionRepository.deleteByTweetIdAndUserId(tweetId, user.getId());
        return ResponseEntity.noContent().build();
    }

    // —— Helpers ——
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private TweetModel getValidTweet(Long tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));
    }

    private Reaction getValidReaction(Long reactionId) {
        return reactionRepository.findById(reactionId)
                .orElseThrow(() -> new RuntimeException("Reaction not found"));
    }
}
