package com.example.movieapp.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.*;


@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String movieId;

    public Favorite() { }

    public Favorite(User user, String movieId) {
        this.user    = user;
        this.movieId = movieId;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getMovieId() {
        return movieId;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }


}