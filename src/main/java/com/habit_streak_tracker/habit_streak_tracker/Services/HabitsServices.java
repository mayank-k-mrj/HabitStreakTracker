package com.habit_streak_tracker.habit_streak_tracker.Services;

import com.habit_streak_tracker.habit_streak_tracker.DTO.HabitCreationRequest;
import com.habit_streak_tracker.habit_streak_tracker.Model.HabitsEntity;

import java.util.List;
import java.util.Optional;

public interface HabitsServices {
    Boolean createHabit(String username, HabitCreationRequest request);
    Boolean updateNameById(Long Id, HabitCreationRequest name);
    Boolean updateDescById(Long Id, HabitCreationRequest desc);
    Boolean updatingFreqById(Long Id, HabitCreationRequest freq);
    List<HabitsEntity> getHabits(String user);
    Optional<HabitsEntity> findHabitsByHabitId(Long habit_id);
    Integer TotalHabits(String username);
}
