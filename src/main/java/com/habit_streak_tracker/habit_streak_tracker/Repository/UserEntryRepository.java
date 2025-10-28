package com.habit_streak_tracker.habit_streak_tracker.Repository;

import com.habit_streak_tracker.habit_streak_tracker.Model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntryRepository extends JpaRepository<UsersEntity, String> {
    Optional<UsersEntity> findByUsername(String username);
    Optional<UsersEntity> findByEmail(String email);
}
