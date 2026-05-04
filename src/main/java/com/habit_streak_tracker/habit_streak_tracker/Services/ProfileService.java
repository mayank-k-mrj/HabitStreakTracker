package com.habit_streak_tracker.habit_streak_tracker.Services;

import com.habit_streak_tracker.habit_streak_tracker.DTO.ProfileFetchingDTO;
import com.habit_streak_tracker.habit_streak_tracker.Model.ProfileEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.UsersEntity;
import com.habit_streak_tracker.habit_streak_tracker.Repository.ProfileEntryRepository;
import org.springframework.stereotype.Service;


public interface ProfileService {

    //Creating new profile row for user every user
    public String CreateProfileRow(String username);

    public Boolean SetData(String username, ProfileEntity profile);
    public ProfileFetchingDTO FetchData(String username);
}
