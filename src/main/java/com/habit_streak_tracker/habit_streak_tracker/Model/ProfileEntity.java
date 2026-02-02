package com.habit_streak_tracker.habit_streak_tracker.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;

import java.time.LocalDate;

@Entity
@DynamicInsert
@Table(name="profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    UsersEntity user;

    @Column(name = "phone", nullable = false)
    private String phone = "+91 0000000000";

    @Column(name = "dob", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob = LocalDate.of(2000, 1, 1);

    @Column(name = "bio", nullable = true)
    private String bio = "Habit Ignition! 🔥";

    @Column(name = "propic", nullable = false)
    private Integer propic = 1;

    public ProfileEntity() {
    }

    public ProfileEntity(UsersEntity user) {
        this.user = user;
    }

    public ProfileEntity(String phone, LocalDate dob, String bio) {
        this.phone = phone;
        this.dob = dob;
        this.bio = bio;
    }

    public ProfileEntity(String phone, LocalDate dob, String bio, Integer propic){
        this.phone = phone;
        this.dob = dob;
        this.bio = bio;
        this.propic = propic;
    }

    public ProfileEntity(UsersEntity user, String phone, LocalDate dob, String bio, Integer propic) {
        this.user = user;
        this.phone = phone;
        this.dob = dob;
        this.bio = bio;
        this.propic = propic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getPropic(){
        return propic;
    }

    public void setPropic(Integer propic){
        this.propic = propic;
    }
}
