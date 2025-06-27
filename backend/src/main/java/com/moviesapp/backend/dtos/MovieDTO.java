package com.moviesapp.backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {
    @JsonProperty("imdbID")
    private String imdbId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("year")
    private String year;
    @JsonProperty("poster")
    private String posterUrl;
    @JsonProperty("isFavorite")
    private boolean isFavorite;
    public MovieDTO() {
    }
    public MovieDTO(String imdbId, String title, String year, String posterUrl, boolean isFavorite) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
        this.isFavorite = isFavorite;
    }

    // Getters
    public String getImdbId()
    { return imdbId; }
    public String getTitle()
    { return title; }

    public String getYear()
    { return year; }
    public String getPosterUrl()
    { return posterUrl; }

    // Setters
    public void   setImdbId(String imdbId)
    { this.imdbId = imdbId; }
    public void   setTitle(String title)
    { this.title = title; }
    public void   setYear(String year)
    { this.year = year; }
    public void   setPosterUrl(String posterUrl)
    { this.posterUrl = posterUrl; }
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}