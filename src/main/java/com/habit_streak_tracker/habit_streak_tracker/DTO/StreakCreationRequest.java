package com.habit_streak_tracker.habit_streak_tracker.DTO;

import java.time.LocalDate;

public record StreakCreationRequest(
        Integer current_streak,
        Integer longest_streak,
        LocalDate lastCompletedDate
){}
