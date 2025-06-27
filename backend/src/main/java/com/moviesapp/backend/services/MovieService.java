package com.moviesapp.backend.services;

import com.moviesapp.backend.dtos.MovieDTO;
import com.moviesapp.backend.dtos.OmdbMovieDTO;
import com.moviesapp.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class MovieService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final FavoriteService favoriteService;

    @Value("${omdb.api.key}")
    private String apiKey;

    @Autowired
    public MovieService(RestTemplate restTemplate, FavoriteService favoriteService) {
        this.baseUrl = "https://www.omdbapi.com/?apikey=%s";
        this.restTemplate = restTemplate;
        this.favoriteService = favoriteService;
    }

    private String getBaseUrl() {
        return String.format(baseUrl, apiKey);
    }

    public MovieDTO getMovie(String title, String movieId, User user) {
        if ((title == null || title.isEmpty()) && (movieId == null || movieId.isEmpty()))
            return null;
        try {
            String baseUrl = getBaseUrl();
            if(title != null && !title.isEmpty())
                baseUrl = baseUrl + "&t=" + URLEncoder.encode(title, StandardCharsets.UTF_8);
            if(movieId != null && !movieId.isEmpty())
                baseUrl = baseUrl + "&i=" + URLEncoder.encode(movieId, StandardCharsets.UTF_8);
            OmdbMovieDTO movie =  restTemplate.getForObject(baseUrl, OmdbMovieDTO.class);
            boolean isFavorite = favoriteService.isFavorite(user.getId(), movie.getImdbId());
            return new MovieDTO(movie.getImdbId(), movie.getTitle(), movie.getYear(), movie.getPosterUrl(), isFavorite);

        } catch (Exception e) {
            throw new RuntimeException("Ha habido un error al obtener la película: " + e.getMessage());
        }
    }
}

