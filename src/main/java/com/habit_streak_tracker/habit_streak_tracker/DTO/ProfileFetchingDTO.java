package com.habit_streak_tracker.habit_streak_tracker.DTO;

import java.time.LocalDate;

public record ProfileFetchingDTO(
        Long id,
        String user,
        String phone,
        LocalDate dob,
        String bio,
        Integer propic
) {
}
