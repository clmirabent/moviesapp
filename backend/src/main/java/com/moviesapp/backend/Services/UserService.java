package com.example.movieapp.service;

import com.example.movieapp.model.User;
import com.example.movieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder)
    {
        this.userRepo= userRepo;
        this.passwordEncoder= passwordEncoder;
    }

    //Method to user´s register
    public User register(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Este usuario ya está registrado.");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Este email ya está registrado.");
        }

        // Password hash
        String rawPass = user.getPassword();
        String encoded = passwordEncoder.encode(rawPass);
        user.setPassword(encoded);

        return userRepo.save(user);
    }

   //Method for user´s login
    public User login(String email, String rawPassword) {
        User user = userRepo.findByEmail(email))
                .orElseThrow(() ->
                        new IllegalArgumentException("No se ha encontrado este usuario")
                );

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }
        return user;
    }
}

