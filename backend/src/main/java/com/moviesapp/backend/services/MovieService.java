package com.moviesapp.backend.services;

import com.moviesapp.backend.dtos.MovieDTO;
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

    @Value("${omdb.api.key}")
    private String apiKey;

    @Autowired
    public MovieService(RestTemplate restTemplate) {
        this.baseUrl = "https://www.omdbapi.com/?apikey=%s";
        this.restTemplate = restTemplate;
    }

    private String getBaseUrl() {
        return String.format(baseUrl, apiKey);
    }

    public MovieDTO getMovie(String title, String movieId) {
        if ((title == null || title.isEmpty()) && (movieId == null || movieId.isEmpty()))
            return null;
        try {
            String baseUrl = getBaseUrl();
            if(title != null && !title.isEmpty())
                baseUrl = baseUrl + "&t=" + URLEncoder.encode(title, StandardCharsets.UTF_8);
            if(movieId != null && !movieId.isEmpty())
                baseUrl = baseUrl + "&i=" + URLEncoder.encode(movieId, StandardCharsets.UTF_8);
            return restTemplate.getForObject(baseUrl, MovieDTO.class);

        } catch (Exception e) {
            throw new RuntimeException("Ha habido un error al obtener la película: " + e.getMessage());
        }
    }
}

