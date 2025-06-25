package com.example.movieapp.service;
import com.example.movieapp.model.Favorite;
import com.example.movieapp.model.User;
import com.example.movieapp.repository.FavoriteRepository;
import com.example.movieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepo;
    private final UserRepository userRepo;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepo, UserRepository userRepo) {
        this.favoriteRepo= favoriteRepo;
        this.userRepo= userRepo;
    }

    //Method to add a favorite movie
    public Favorite addFavorite(Long userId, String movieId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Este usuario no existe en nuestra web"));

        Favorite favorite = new Favorite(user, movieId);
        return favoriteRepo.save(favorite);
    }

    //Method to remove a favorite
    public void removeFavorite(Long favoriteId) {
        favoriteRepo.deleteById(favoriteId);
    }

    //Method to display a user´s favorite list
    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepo.findByUserId(userId);
    }

    //Method to verify if a movie is already in the user´s favorite list
    public boolean isMovieAlreadyFavorite(Long userId, String movieId) {
        return favoriteRepo.findByUserId(userId).stream()
                .anyMatch(fav -> fav.getMovieId().equals(movieId));
    }
}
