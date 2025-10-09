package com.habit_streak_tracker.habit_streak_tracker.Repository;

import com.habit_streak_tracker.habit_streak_tracker.Model.HabitsEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.StreaksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StreakEntryRepository extends JpaRepository<StreaksEntity, Long> {
    Optional<StreaksEntity> findByHabitId(Long id);
    StreaksEntity findByHabit_Id(Long id);
}