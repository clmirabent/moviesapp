package com.moviesapp.backend.repositories;

import com.moviesapp.backend.models.Favorite;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoriteRepository extends JpaRepository<Favorite,  Long> {
    // Search an user´s favorites
    List<Favorite> findByUserId(Long userId);

    // Find by User and MovieId
    Optional<Favorite> findByUserIdAndMovieId(Long userId, String movieId);

    // Verify a specific favorite
    boolean existsByUserIdAndMovieId(Long userId, String movieId);
}