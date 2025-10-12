package com.habit_streak_tracker.habit_streak_tracker.Services;

import com.habit_streak_tracker.habit_streak_tracker.DTO.StreakCreationRequest;
import com.habit_streak_tracker.habit_streak_tracker.DTO.StreakDTO;
import com.habit_streak_tracker.habit_streak_tracker.Model.HabitsEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.StreaksEntity;
import com.habit_streak_tracker.habit_streak_tracker.Repository.HabitsEntryRepository;
import com.habit_streak_tracker.habit_streak_tracker.Repository.StreakEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.List;

@Service
public class StreakService_Imp implements StreakService{

    @Autowired
    private HabitsEntryRepository habitsEntryRepository;

    @Autowired
    private StreakEntryRepository streakEntryRepository;

    public StreakDTO findAllStreaks(Long id){
        StreaksEntity allStreaks = streakEntryRepository.findByHabit_Id(id);

        return new StreakDTO(
                allStreaks.getId(),
                allStreaks.getCurrent_streak(),
                allStreaks.getLongest_streak(),
                allStreaks.getLastCompleteDate(),
                allStreaks.getHabit().getId()
        );
    }

    public StreaksEntity markHabitsCompleted(Long habit_id){
        StreaksEntity streaks = streakEntryRepository.findByHabitId(habit_id)
                .orElseThrow(() -> new RuntimeException("Streak not found : "+habit_id));


        LocalDate todayDate = LocalDate.now();
        LocalDate lastCompDate = streaks.getLastCompleteDate();

        //Checking for already completed
        if(lastCompDate != null && lastCompDate.isEqual(todayDate)){
            return streaks;
        }

        //Checking for firstTime completing habit
        if(lastCompDate == null){
            streaks.setCurrent_streak(1);
            streaks.setLongest_streak(1);
            streaks.setLastCompleteDate(todayDate);
            return streakEntryRepository.save(streaks);
        }

        //Logic for checking streak is broken or not
        HabitsEntity habit = streaks.getHabit();

        Boolean brokenStreak = false;
        switch (habit.getFrequency().toLowerCase()){
            case "weekly":
                if(ChronoUnit.DAYS.between(lastCompDate, todayDate) > 7){
                    brokenStreak = true;
                }
                break;

            case "monthly":
                if(!java.time.YearMonth.from(lastCompDate).equals(java.time.YearMonth.from(todayDate))){
                    if(java.time.YearMonth.from(lastCompDate).plusMonths(1).equals(java.time.YearMonth.from(todayDate))){
                        brokenStreak = false;
                    }
                    else{
                        brokenStreak = true;
                    }
                }
                break;

            default :
                if(ChronoUnit.DAYS.between(lastCompDate, todayDate) > 1){
                    brokenStreak = true;
                }
                break;
        }

        //Checking for duplicate date
        Boolean duplicateDate = false;
        if(!brokenStreak){
            switch (habit.getFrequency().toLowerCase()){
                case "weekly":
                    if (lastCompDate.isEqual(todayDate)) {
                        duplicateDate = true;
                    }
                    break;
                case "monthly":
                    if (lastCompDate.getMonth() == todayDate.getMonth() && lastCompDate.getYear() == todayDate.getYear()) {
                        duplicateDate = true;
                    }
                    break;
            }
        }

        if(brokenStreak){
            streaks.setCurrent_streak(1);
        } else if (!duplicateDate) {
            streaks.setCurrent_streak(streaks.getCurrent_streak() +  1);
        }

        if(streaks.getCurrent_streak() > streaks.getLongest_streak()){
            streaks.setLongest_streak(streaks.getCurrent_streak());
        }
        streaks.setLastCompleteDate(todayDate);

        return streakEntryRepository.save(streaks);
    }

    public Integer findLongestStreak(Long streak_id){
        StreaksEntity streak = streakEntryRepository.findById(streak_id)
                .orElseThrow(() -> new RuntimeException("Longest Streak not found with id : "+streak_id));

        return streak.getLongest_streak();

    }

    public Integer findCurrentStreak(Long  streak_id){
        StreaksEntity streak = streakEntryRepository.findById(streak_id)
                .orElseThrow(() -> new RuntimeException("Current Streak not found with id : "+streak_id));

        return streak.getCurrent_streak();
    }

    public LocalDate findLastCompletedDate(Long streak_id){
        StreaksEntity streak = streakEntryRepository.findById(streak_id)
                .orElseThrow(() -> new RuntimeException("Last Completed Date not found with id : "+streak_id));

        return streak.getLastCompleteDate();
    }

    @Override
    public Boolean deletehabitById(Long habit_id) {
        streakEntryRepository.deleteById(habit_id);
        StreaksEntity streak = streakEntryRepository.findByHabit_Id(habit_id);
        if(streak == null){
            return true;
        }
        else{
            return false;
        }
    }
}
