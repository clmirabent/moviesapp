package com.moviesapp.backend.controllers;

import com.moviesapp.backend.dtos.FavoriteDTO;
import com.moviesapp.backend.models.User;
import com.moviesapp.backend.services.AuthenticationService;
import com.moviesapp.backend.services.FavoriteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
 @RequestMapping("/api/favorites")
public class FavoriteController {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);
    private final FavoriteService favoriteService;
    private final AuthenticationService authenticationService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService, AuthenticationService authenticationService) {
        this.favoriteService = favoriteService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(@RequestBody FavoriteDTO favoriteDTO) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            FavoriteDTO dto = favoriteService.addFavorite(
                    email,
                    favoriteDTO.getMovieId()
            );
            return ResponseEntity.ok(dto);

        } catch (ResponseStatusException ex) {
            logger.warn("Error añadiendo favorito: {}", ex.getReason());
            return ResponseEntity.status(ex.getStatusCode()).build();

        } catch (IllegalArgumentException ex) {
            logger.warn("Datos inválidos al añadir favorito: {}", ex.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (Exception ex) {
            logger.error("Error inesperado en addFavorite()", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteFavorite(
            @PathVariable String movieId) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = authenticationService.loadByEmail(email);
            favoriteService.removeFavorite(user.getId(), movieId);
            return ResponseEntity.noContent().build();

        } catch (ResponseStatusException ex) {
            logger.warn("Error borrando favorito: {}", ex.getReason());
            return ResponseEntity.status(ex.getStatusCode()).build();

        } catch (Exception ex) {
            logger.error("Error inesperado en deleteFavorite()", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUser() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = authenticationService.loadByEmail(email);
            List<FavoriteDTO> list = favoriteService.getFavoritesByUser(user.getId());
            if (list == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(list);

        } catch (ResponseStatusException ex) {
            logger.warn("Usuario no encontrado para favoritos: {}", ex.getReason());
            return ResponseEntity.status(ex.getStatusCode()).build();

        } catch (Exception ex) {
            logger.error("Error inesperado en getFavoritesByUser()", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

