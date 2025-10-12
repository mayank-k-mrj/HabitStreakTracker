package com.habit_streak_tracker.habit_streak_tracker.Services;

import com.habit_streak_tracker.habit_streak_tracker.DTO.StreakCreationRequest;
import com.habit_streak_tracker.habit_streak_tracker.DTO.StreakDTO;
import com.habit_streak_tracker.habit_streak_tracker.Model.StreaksEntity;

import java.time.LocalDate;
import java.util.List;

public interface StreakService {
    StreaksEntity markHabitsCompleted(Long habit_id);
    StreakDTO findAllStreaks(Long id);
    Integer findLongestStreak(Long habit);
    Integer findCurrentStreak(Long habit);
    LocalDate findLastCompletedDate(Long habit);
    Boolean deletehabitById(Long habit_id);
}
