package com.habit_streak_tracker.habit_streak_tracker.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "habits")
public class HabitsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long habitid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "frequency", nullable = false)
    private String frequency;

    @Column(name = "startdate", nullable = false)
    private LocalDate startdate;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime created_date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private UsersEntity user;

    public HabitsEntity(LocalDate startdate, String frequency, String description, String name) {
        this.startdate = startdate;
        this.frequency = frequency;
        this.description = description;
        this.name = name;
    }

    public HabitsEntity() {
    }

    public Long getId() {
        return habitid;
    }

    public void setId(Long habitid) {
        this.habitid = habitid;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
