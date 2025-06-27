package com.moviesapp.backend.controllers;

import com.moviesapp.backend.dtos.AuthDTO;
import com.moviesapp.backend.dtos.JwtResponseDTO;
import com.moviesapp.backend.dtos.UserDTO;
import com.moviesapp.backend.security.JwtService;
import com.moviesapp.backend.security.UserInfoService;
import com.moviesapp.backend.services.AuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;

    @Autowired
    JwtService jwtService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;

    }

    //Method for register
    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> register(@RequestBody AuthDTO authDTO) {
        try {
            UserDTO createdDto = authenticationService.register(authDTO);

            String jwtToken = jwtService.generateToken(createdDto.getEmail());

            JwtResponseDTO response = new JwtResponseDTO(jwtToken, createdDto);
            return ResponseEntity.ok(response);

        } catch (ResponseStatusException ex) {
            logger.warn("Registro fallido: {}", ex.getReason());
            return ResponseEntity.status(ex.getStatusCode()).build();
        } catch (IllegalArgumentException ex) {
            logger.warn("Datos inválidos en registro: {}", ex.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            logger.error("Error inesperado en registro", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //Method for login
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody AuthDTO dto) {
        try {
            UserDTO userDto = authenticationService.login(dto);

            String jwtToken = jwtService.generateToken(userDto.getEmail());
            JwtResponseDTO response = new JwtResponseDTO(jwtToken, userDto);
            return ResponseEntity.ok(response);

        } catch (ResponseStatusException ex) {
            logger.warn("Login fallido: {}", ex.getReason());
            return ResponseEntity.status(ex.getStatusCode()).build();
        } catch (Exception ex) {
            logger.error("Error inesperado en login", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
