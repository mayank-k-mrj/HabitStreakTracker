package com.habit_streak_tracker.habit_streak_tracker.DTO;

import java.time.LocalDate;

public class StreakDTO {

    private Long id;
    private Integer currentStreak;
    private Integer longestStreak;
    private LocalDate lastCompletedDate;
    private Long habitId;

    public StreakDTO(Long id, Integer currentStreak, Integer longestStreak, LocalDate lastCompleteDate, Long habitId) {
        this.id = id;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.habitId = habitId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public Integer getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(Integer longestStreak) {
        this.longestStreak = longestStreak;
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public LocalDate getLastCompletedDate() {
        return lastCompletedDate;
    }

    public void setLastCompletedDate(LocalDate lastCompletedDate) {
        this.lastCompletedDate = lastCompletedDate;
    }
}
