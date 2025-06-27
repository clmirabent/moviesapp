package com.moviesapp.backend.dtos;

import jakarta.validation.constraints.NotNull;

public class FavoriteDTO {
    @NotNull
    private String movieId;

    public FavoriteDTO(String movieId, String username) {
        this.movieId  = movieId;
    }

    public String getMovieId() {
        return movieId;
    }
}
