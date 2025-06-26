package com.moviesapp.backend.dtos;

import com.moviesapp.backend.models.Favorite;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


public class UserDTO {
    @NotNull
    private String userName;

    @NotNull
    private String email;

    private Long Id;

    private List<Favorite> favorites = new ArrayList<>();

    public UserDTO(Long id, String userName, String email) {
        this.email  = email;
        this.userName = userName;
        this.Id = id;
    }

    public Long getId() {
        return this.Id;
    }

    public String getUserName(String userName) {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }
}

