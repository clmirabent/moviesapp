package com.moviesapp.backend.services;

import com.moviesapp.backend.dtos.AuthDTO;
import com.moviesapp.backend.dtos.UserDTO;
import com.moviesapp.backend.models.User;
import com.moviesapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AuthenticationService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthenticationService(UserRepository userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO register(AuthDTO dto) {
        if (userRepo.existsByUsername(dto.getUserName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este usuario ya está registrado."
            );
        }
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este email ya está registrado."
            );
        }

        User user = new User();
        user.setUsername(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepo.save(user);
        return toDto(saved);
    }

    public UserDTO login(AuthDTO dto) {
        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe ningún usuario con ese email."
                ));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Contraseña incorrecta."
            );
        }

        return toDto(user);
    }

    public UserDTO getById(Long id) {
        User user = userRepo.findUserById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado."
                ));
        return toDto(user);
    }

    public User loadUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado."
                ));
    }

    private UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getUserName(),
                user.getEmail()
        );
    }
}


