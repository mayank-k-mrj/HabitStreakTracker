package com.habit_streak_tracker.habit_streak_tracker.Repository;

import com.habit_streak_tracker.habit_streak_tracker.Model.HabitsEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.UsersEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HabitsEntryRepository extends JpaRepository<HabitsEntity, Long> {
    List<HabitsEntity> findByUser_Username(String username);
    List<HabitsEntity> findHabitIdByUser_Username(String username);
    Optional<HabitsEntity> findAllById(Long habit_id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM habits s WHERE s.habitid = :habitId",
            nativeQuery = true)
    void deleteByHabitId(@Param("habitId") Long habitId);
    Integer countByUser_Username(String username);
}
