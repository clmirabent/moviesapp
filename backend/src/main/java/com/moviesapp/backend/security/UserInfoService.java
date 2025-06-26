package com.moviesapp.backend.security;

import com.moviesapp.backend.models.User;
import com.moviesapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserInfoService(UserRepository repository) {
        this.repository = repository;
    }

    // Method to load user details by username (email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database by email (username)
        Optional<User> userInfo = repository.findByEmail(username);

        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Convert UserInfo to UserDetails (UserInfoDetails)
        User user = userInfo.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of());
    }
}