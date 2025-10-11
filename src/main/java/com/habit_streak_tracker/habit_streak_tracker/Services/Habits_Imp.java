package com.habit_streak_tracker.habit_streak_tracker.Services;

import com.habit_streak_tracker.habit_streak_tracker.DTO.HabitCreationRequest;
import com.habit_streak_tracker.habit_streak_tracker.Model.HabitsEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.StreaksEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.UsersEntity;
import com.habit_streak_tracker.habit_streak_tracker.Repository.HabitsEntryRepository;
import com.habit_streak_tracker.habit_streak_tracker.Repository.StreakEntryRepository;
import com.habit_streak_tracker.habit_streak_tracker.Repository.UserEntryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class Habits_Imp implements HabitsServices{


    @Autowired
    private StreakEntryRepository streakEntryRepository;

    @Autowired
    private HabitsEntryRepository habitsEntryRepository;

    @Autowired
    private UserEntryRepository userEntryRepository;

    public Boolean getHabits(HabitsEntity habitsEntity){
        habitsEntryRepository.save(habitsEntity);
        return true;
    }

    @Transactional
    public Boolean createHabit(String username, HabitCreationRequest request) {
        UsersEntity currentUser = userEntryRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        HabitsEntity newHabit = new HabitsEntity();

        newHabit.setName(request.name());
        newHabit.setDescription(request.description());
        newHabit.setFrequency(request.frequency());
        newHabit.setStartdate(request.startDate());
        newHabit.setUser(currentUser);

        HabitsEntity savedhabit = habitsEntryRepository.save(newHabit);

        //Here I am creating a service for saving a streak row when new habit is created.

        StreaksEntity streaksEntity = new StreaksEntity();

        streaksEntity.setHabit(savedhabit);
        streaksEntity.setCurrent_streak(0);
        streaksEntity.setLongest_streak(0);
        streaksEntity.setLastCompleteDate(null);

        streakEntryRepository.save(streaksEntity);

        return true;
    }

    public Boolean updateNameById(Long id, HabitCreationRequest name){
        HabitsEntity currentName = habitsEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Name not found with id : "+id));
        String updatedName = name.name();
        currentName.setName(updatedName);

        HabitsEntity savedName = habitsEntryRepository.save(currentName);

        return true;
    }

    public Boolean updateDescById(Long id, HabitCreationRequest desc){
        HabitsEntity currentDesc = habitsEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found with id : "+id));

        String updatedDesc = desc.description();
        currentDesc.setDescription(updatedDesc);

        HabitsEntity savedDesc = habitsEntryRepository.save(currentDesc);

        return true;
    }

    @Override
    public Boolean updatingFreqById(Long id, HabitCreationRequest freq) {
        HabitsEntity currentFreq = habitsEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Frequency not found with id : "+id));

        String updatedFreq = freq.frequency();
        currentFreq.setFrequency(updatedFreq);

        HabitsEntity savedFreq = habitsEntryRepository.save(currentFreq);

        return true;
    }

    public List<HabitsEntity> getHabits(String username){
        return habitsEntryRepository.findByUser_Username(username);
    }

    public Optional<HabitsEntity> findHabitsByHabitId(Long habit_id){
        return habitsEntryRepository.findById(habit_id);
    }

}
