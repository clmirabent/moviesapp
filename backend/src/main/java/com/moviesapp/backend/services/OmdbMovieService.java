package com.moviesapp.backend.services;

import com.moviesapp.backend.dtos.OmdbMovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class OmdbMovieService {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    @Value("${omdb.api.key}")
    private String apiKey;

    @Autowired
    public OmdbMovieService(RestTemplate restTemplate) {
        this.baseUrl = "https://www.omdbapi.com/?apikey=%s";
        this.restTemplate = restTemplate;
    }

    private String getBaseUrl() {
        return String.format(baseUrl, apiKey);
    }

    public OmdbMovieDTO getOmdbMovie(String title, String movieId) {
        String baseUrl = getBaseUrl();
        if(title != null && !title.isEmpty())
            baseUrl = baseUrl + "&t=" + URLEncoder.encode(title, StandardCharsets.UTF_8);
        if(movieId != null && !movieId.isEmpty())
            baseUrl = baseUrl + "&i=" + URLEncoder.encode(movieId, StandardCharsets.UTF_8);
        return restTemplate.getForObject(baseUrl, OmdbMovieDTO.class);
    }
}
