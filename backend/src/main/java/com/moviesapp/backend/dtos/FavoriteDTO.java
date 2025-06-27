package com.moviesapp.backend.dtos;

import jakarta.validation.constraints.NotNull;

public class FavoriteDTO {
    @NotNull
    private String movieId;
    @NotNull
    private String username;

    public FavoriteDTO(String movieId, String username) {
        this.movieId  = movieId;
        this.username = username;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getUsername() {
        return username;
    }
}
