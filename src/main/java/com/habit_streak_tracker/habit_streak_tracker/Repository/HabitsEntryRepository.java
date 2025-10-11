package com.habit_streak_tracker.habit_streak_tracker.Repository;

import com.habit_streak_tracker.habit_streak_tracker.Model.HabitsEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitsEntryRepository extends JpaRepository<HabitsEntity, Long> {
    List<HabitsEntity> findByUser_Username(String username);
    List<HabitsEntity> findHabitIdByUser_Username(String username);

    Optional<HabitsEntity> findAllById(Long habit_id);
}
