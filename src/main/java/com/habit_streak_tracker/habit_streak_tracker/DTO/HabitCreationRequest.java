package com.habit_streak_tracker.habit_streak_tracker.DTO;

import java.time.LocalDate;

public record HabitCreationRequest(
        String name,
        String description,
        String frequency,
        LocalDate startDate
) {}