package com.moviesapp.backend.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public class FavoriteDTO {
    @NotNull
    private String movieId;
    @Nullable
    private MovieDTO movieDTO;

    public FavoriteDTO() {}

    public FavoriteDTO(String movieId) {
        this.movieId  = movieId;
    }

    public FavoriteDTO(String movieId, MovieDTO movieDTO) {
        this.movieId  = movieId;
        this.movieDTO = movieDTO;
    }

    public String getMovieId() {
        return movieId;
    }

    public MovieDTO getMovieDTO() {
        return movieDTO;
    }

    public void setMovieDTO(MovieDTO movieDTO) {
        this.movieDTO = movieDTO;
    }
}
