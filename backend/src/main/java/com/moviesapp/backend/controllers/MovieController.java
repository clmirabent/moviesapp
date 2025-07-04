package com.moviesapp.backend.controllers;

import com.moviesapp.backend.dtos.MovieDTO;
import com.moviesapp.backend.models.User;
import com.moviesapp.backend.services.AuthenticationService;
import com.moviesapp.backend.services.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;
    private final AuthenticationService authenticationService;

    @Autowired
    public MovieController(MovieService movieService, AuthenticationService authenticationService) {
        this.movieService = movieService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<MovieDTO> getMovies(
            @RequestParam(required = false) String movieId,
            @RequestParam(required = false) String title) {
        try {
            if ((movieId == null || movieId.isEmpty()) &&
                    (title   == null || title.isEmpty())) {
                return ResponseEntity.badRequest().build();
            }
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = authenticationService.loadByEmail(email);
            MovieDTO dto = movieService.getMovie(title, movieId, user);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(dto);

        } catch (IllegalArgumentException ex) {
            logger.warn("Bad request en getMovies: {}t", ex.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (Exception ex) {
            logger.error("Error al obtener la película", ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
