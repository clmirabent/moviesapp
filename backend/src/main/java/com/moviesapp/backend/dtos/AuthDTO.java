package com.moviesapp.backend.dtos;

import jakarta.validation.constraints.NotNull;

public class AuthDTO {

    private String userName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    public AuthDTO(String userName, String email, String password) {
        this.email  = email;
        this.userName = userName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
