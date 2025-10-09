package com.habit_streak_tracker.habit_streak_tracker.Cotroller;

import com.habit_streak_tracker.habit_streak_tracker.DTO.HabitCreationRequest;
import com.habit_streak_tracker.habit_streak_tracker.DTO.StreakCreationRequest;
import com.habit_streak_tracker.habit_streak_tracker.DTO.StreakDTO;
import com.habit_streak_tracker.habit_streak_tracker.Model.HabitsEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.StreaksEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.UsersEntity;
import com.habit_streak_tracker.habit_streak_tracker.Services.HabitsServices;
import com.habit_streak_tracker.habit_streak_tracker.Services.StreakService;
import com.habit_streak_tracker.habit_streak_tracker.Services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hst")
public class HabitStreakTrackerController {

    //Here are some endpoints for signup and signin
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersServices usersServices;

    @PostMapping("/signup")
    public Boolean getSignup(@RequestBody UsersEntity entries){
        UsersEntity newUser = new UsersEntity();
        newUser.setUsername(entries.getUsername());
        newUser.setEmail(entries.getEmail());
        String hashedPassword = passwordEncoder.encode(entries.getPassword());
        newUser.setPassword(hashedPassword);
        usersServices.register(newUser);

        return true;
    }

    //---------------------------------------------------------------------------------------------------------------//

    //Here are the endpoints to ive habits data.

    @Autowired
    private HabitsServices habitsServices;

    @Autowired
    private StreakService streakService;

    //Fetching All habits
    @GetMapping("habits/{user}/userhabits")
    public List<HabitsEntity> getAllStreaks(Principal principal){
        String username = principal.getName();
        return habitsServices.getHabits(username);
    }

    @PostMapping("/habits")
    public ResponseEntity<String> createNewHabit(@RequestBody HabitCreationRequest request, Principal principal) {
        // 1. Get the username of the logged-in user from Spring Security
        String username = principal.getName();

        // 2. Call the new, correct service to create the habit
        habitsServices.createHabit(username, request);

        return ResponseEntity.ok("Habit '" + request.name() + "' created successfully for user: " + username);
    }

    //Updating Name
    @PutMapping("habits/{id}/name")
    public ResponseEntity<String> updateName(@PathVariable Long id, @RequestBody HabitCreationRequest name){
        habitsServices.updateNameById(id, name);

        return ResponseEntity.ok("New name : " + name.name());
    }

    //Updating Description
    @PutMapping("habits/{id}/desc")
    public ResponseEntity<String> updatingDesc(@PathVariable Long id, @RequestBody HabitCreationRequest desc){
        habitsServices.updateDescById(id, desc);

        return ResponseEntity.ok("New Description : " + desc.description());
    }

    //Updating Frequency
    @PutMapping("habits/{id}/freq")
    public ResponseEntity<String> updatingFreq(@PathVariable Long id, @RequestBody HabitCreationRequest freq){
        habitsServices.updatingFreqById(id, freq);

        return ResponseEntity.ok("New Frequency : " + freq.frequency());
    }

    //------------------------------------------------------------------------------------------------------------------//
    //Here are some endpoints to get the streak data from the user.

    //Fetching currentStreak
    @GetMapping("habits/{id}/streaks")
    public StreakDTO getAllStreaks(@PathVariable Long id){
        return streakService.findAllStreaks(id);
    }

    @PostMapping("habits/{habit}/compstreak")
    public ResponseEntity<String> compHabit(@PathVariable Long habit){
        streakService.markHabitsCompleted(habit);
        return ResponseEntity.ok("Completion Done");
    }
}
