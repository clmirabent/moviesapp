package com.moviesapp.backend.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public class FavoriteDTO {
    @NotNull
    private String movieId;
    @Nullable
    private OmdbMovieDTO omdbMovieDTO;

    public FavoriteDTO() {}

    public FavoriteDTO(String movieId) {
        this.movieId  = movieId;
    }

    public FavoriteDTO(String movieId, OmdbMovieDTO omdbMovieDTO) {
        this.movieId  = movieId;
        this.omdbMovieDTO = omdbMovieDTO;
    }

    public String getMovieId() {
        return movieId;
    }

    public OmdbMovieDTO getOmdbMovieDTO() {
        return omdbMovieDTO;
    }

    public void setOmdbMovieDTO(OmdbMovieDTO omdbMovieDTO) {
        this.omdbMovieDTO = omdbMovieDTO;
    }
}
