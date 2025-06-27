package com.moviesapp.backend.services;

import com.moviesapp.backend.dtos.MovieDTO;
import com.moviesapp.backend.dtos.OmdbMovieDTO;
import com.moviesapp.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final FavoriteService favoriteService;
    private final OmdbMovieService omdbMovieService;

    @Autowired
    public MovieService(FavoriteService favoriteService, OmdbMovieService omdbMovieService) {
        this.favoriteService = favoriteService;
        this.omdbMovieService = omdbMovieService;
    }

    public MovieDTO getMovie(String title, String movieId, User user) {
        if ((title == null || title.isEmpty()) && (movieId == null || movieId.isEmpty()))
            return null;
        try {
            OmdbMovieDTO movie = omdbMovieService.getOmdbMovie(title, movieId);
            boolean isFavorite = favoriteService.isFavorite(user.getId(), movie.getImdbId());
            return new MovieDTO(movie.getImdbId(), movie.getTitle(), movie.getYear(), movie.getPosterUrl(), isFavorite);

        } catch (Exception e) {
            throw new RuntimeException("Ha habido un error al obtener la película: " + e.getMessage());
        }
    }
}

