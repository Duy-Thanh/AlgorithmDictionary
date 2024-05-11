package com.algorithmswiki.apps.backend.backend.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Algorithms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;
    private LocalDateTime dateAdded;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    // Getters and Setters
    public String getAlgorithmsContents() {
        return contents;
    }

    public String getAlgorithmsDateTimeAdded() {
        return dateAdded.toString();
    }

    public UserAccount getAlgorithmsUserAccount() {
        return userAccount;
    }

    public void setAlgorithmsContents(String contents) {
        this.contents = contents;
    }
}