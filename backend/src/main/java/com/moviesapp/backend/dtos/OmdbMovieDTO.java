package com.moviesapp.backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbMovieDTO {
    @JsonProperty("imdbID")
    private String imdbId;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("Poster")
    private String posterUrl;
    public OmdbMovieDTO() {
    }
    public OmdbMovieDTO(String imdbId, String title, String year, String posterUrl) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
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
}