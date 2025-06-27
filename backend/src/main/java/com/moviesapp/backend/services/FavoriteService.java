package com.moviesapp.backend.services;

import com.moviesapp.backend.dtos.FavoriteDTO;
import com.moviesapp.backend.dtos.MovieDTO;
import com.moviesapp.backend.models.Favorite;
import com.moviesapp.backend.models.User;
import com.moviesapp.backend.repositories.FavoriteRepository;
import com.moviesapp.backend.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepo;
    private final UserRepository userRepo;
    private final OmdbMovieService omdbMovieService;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepo, UserRepository userRepo, OmdbMovieService omdbMovieService)
    {
        this.favoriteRepo= favoriteRepo;
        this.userRepo= userRepo;
        this.omdbMovieService = omdbMovieService;
    }

    //Method to add a favorite to the list
    public FavoriteDTO addFavorite(String email, String movieId) {
        //Search for user
        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Este usuario no existe en nuestra web"
                        )
                );

        // Search for duplicated favorite
        if (favoriteRepo.existsByUserIdAndMovieId(user.getId(), movieId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta película ya está en favoritos"
            );
        }
        Favorite saved = favoriteRepo.save(new Favorite(user, movieId));
        return toDto(saved);
    }

    //Method to delete favorite
    public void removeFavorite(long userId, String movieId) {
        Favorite favorite = favoriteRepo.findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorito no encontrado"));
        favoriteRepo.deleteById(favorite.getId());
    }

    //Method to get the favorites
    public List<FavoriteDTO> getFavoritesByUser(Long userId) {
        if (!userRepo.existsById(userId)) {
            return null;
        }
        // TODO: To improve performance add a cache
        return favoriteRepo.findByUserId(userId).stream()
                .map(this::toDto)
                .map(this::addOmdbFavorite)
                .collect(Collectors.toList());
    }

    public boolean isFavorite(long userId, String movieId) {
        return favoriteRepo.findByUserIdAndMovieId(userId, movieId).isPresent();
    }

    private FavoriteDTO addOmdbFavorite(FavoriteDTO favorite) {
        var omdbMovie = omdbMovieService.getOmdbMovie(null, favorite.getMovieId());
        return new FavoriteDTO(favorite.getMovieId(),
                new MovieDTO(
                        omdbMovie.getImdbId(),
                        omdbMovie.getTitle(),
                        omdbMovie.getYear(),
                        omdbMovie.getPosterUrl(),
                        true));
    }
    private FavoriteDTO toDto(Favorite fav) {
        return new FavoriteDTO(
                fav.getMovieId()
        );
    }
}
