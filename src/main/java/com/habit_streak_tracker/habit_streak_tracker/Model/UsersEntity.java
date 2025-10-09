package com.habit_streak_tracker.habit_streak_tracker.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UsersEntity {

    @Id
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "authorities", nullable = false)
    private String authorities = "ROLE_USER";

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public UsersEntity() {
    }

    public UsersEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = true;
        this.authorities = "ROLE_USER";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}

