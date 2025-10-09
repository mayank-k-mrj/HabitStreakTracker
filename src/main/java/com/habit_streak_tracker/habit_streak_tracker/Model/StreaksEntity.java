package com.habit_streak_tracker.habit_streak_tracker.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "streaks")
public class StreaksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_streak", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer current_streak = 0;

    @Column(name = "longest_streak", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer longest_streak = 0;

    @Column(name = "lastCompleteDate")
    private LocalDate lastCompleteDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private HabitsEntity habit;

    public StreaksEntity(Integer current_streak, Integer longest_streak, LocalDate lastCompleteDate) {
        this.current_streak = current_streak;
        this.longest_streak = longest_streak;
        this.lastCompleteDate = lastCompleteDate;
    }

    public HabitsEntity getHabit() {
        return habit;
    }

    public void setHabit(HabitsEntity habit) {
        this.habit = habit;
    }

    public StreaksEntity() {
    }

    public Integer getLongest_streak() {
        return longest_streak;
    }

    public void setLongest_streak(Integer longest_streak) {
        this.longest_streak = longest_streak;
    }

    public Integer getCurrent_streak() {
        return current_streak;
    }

    public void setCurrent_streak(Integer current_streak) {
        this.current_streak = current_streak;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HabitsEntity getStreak_id() {
        return habit;
    }

    public void setStreak_id(HabitsEntity habit) {
        this.habit = habit;
    }

    public LocalDate getLastCompleteDate() {
        return lastCompleteDate;
    }

    public void setLastCompleteDate(LocalDate lastCompleteDate) {
        this.lastCompleteDate = lastCompleteDate;
    }
}
