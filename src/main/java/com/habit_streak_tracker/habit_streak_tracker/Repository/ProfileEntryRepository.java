package com.habit_streak_tracker.habit_streak_tracker.Repository;

import com.habit_streak_tracker.habit_streak_tracker.Model.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileEntryRepository extends JpaRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByUser_Username(String username);
}
