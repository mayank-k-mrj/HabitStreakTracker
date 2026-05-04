package com.habit_streak_tracker.habit_streak_tracker.Cotroller;

import com.habit_streak_tracker.habit_streak_tracker.DTO.HabitCreationRequest;
import com.habit_streak_tracker.habit_streak_tracker.DTO.ProfileFetchingDTO;
import com.habit_streak_tracker.habit_streak_tracker.DTO.StreakCreationRequest;
import com.habit_streak_tracker.habit_streak_tracker.DTO.StreakDTO;
import com.habit_streak_tracker.habit_streak_tracker.Model.HabitsEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.ProfileEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.StreaksEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.UsersEntity;
import com.habit_streak_tracker.habit_streak_tracker.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
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

        profileService.CreateProfileRow(entries.getUsername());

        return true;
    }

    //---------------------------------------------------------------------------------------------------------------//

    //Here are the endpoints to ive habits data.

    @Autowired
    private HabitsServices habitsServices;

    @Autowired
    private StreakService streakService;

    //Fetching All habits
    @GetMapping("/habits/{user}/userhabits")
    public List<HabitsEntity> getAllStreaks(Principal principal){
        String username = principal.getName();
        return habitsServices.getHabits(username);
    }

    @GetMapping("/habits/{user}/totalhabit")
    public Integer totalHabit(Principal principal){
        String username = principal.getName();
        return habitsServices.TotalHabits(username);
    }

    @GetMapping("/habits/{habit_id}/habitsbyid")
    public Optional<HabitsEntity> getHabitsById(@PathVariable Long habit_id){
        return habitsServices.findHabitsByHabitId(habit_id);
    }

    //here I am creating habits
    @PostMapping("/habits")
    public ResponseEntity<String> createNewHabit(@RequestBody HabitCreationRequest request, Principal principal) {
        String username = principal.getName();

        habitsServices.createHabit(username, request);

        return ResponseEntity.ok("Habit '" + request.name() + "' created successfully for user: " + username);
    }

    @GetMapping("/user")
    public String getUser(Principal principal){
        return principal.getName();
    }

    //Updating Name
    @PutMapping("/habits/{id}/name")
    public ResponseEntity<String> updateName(@PathVariable Long id, @RequestBody HabitCreationRequest name){
        habitsServices.updateNameById(id, name);

        return ResponseEntity.ok("New name : " + name.name());
    }

    //Updating Description
    @PutMapping("/habits/{id}/desc")
    public ResponseEntity<String> updatingDesc(@PathVariable Long id, @RequestBody HabitCreationRequest desc){
        habitsServices.updateDescById(id, desc);

        return ResponseEntity.ok("New Description : " + desc.description());
    }

    //Updating Frequency
    @PutMapping("/habits/{id}/freq")
    public ResponseEntity<String> updatingFreq(@PathVariable Long id, @RequestBody HabitCreationRequest freq){
        habitsServices.updatingFreqById(id, freq);

        return ResponseEntity.ok("New Frequency : " + freq.frequency());
    }

    //------------------------------------------------------------------------------------------------------------------//
    //Here are some endpoints to get the streak data from the user.

    //Fetching currentStreak
    @GetMapping("/habits/{id}/streaks")
    public StreakDTO getAllStreaks(@PathVariable Long id){
        return streakService.findAllStreaks(id);
    }

    @GetMapping("/habits/{id}/currentStr")
    public Integer findCurrentStreak(@PathVariable Long id){
        return  streakService.findCurrentStreak(id);
    }

    //Here I am completing streaks
    @PostMapping("/habits/{habit}/compstreak")
    public ResponseEntity<String> compHabit(@PathVariable Long habit){
        streakService.markHabitsCompleted(habit);
        return ResponseEntity.ok("Completion Done");
    }

    @DeleteMapping("/habits/{id}/delhabit")
    public Boolean delById(@PathVariable Long id){
        streakService.deletehabitById(id);
        return true;
    }

    //-----------------------------------------------------------------------------------------------------------------//
    //Creating endpoints for editing profile.

    @Autowired
    private ProfileService profileService;

    //Healthcheck Endpoint
    @GetMapping("/profile")
    public String profile(){
        return "Profile Editing";
    }

    //Endpoint for setting the data in database
    @PutMapping("/profile/editdata")
    public ResponseEntity<String> EditData(@RequestBody ProfileEntity data, Principal principal){
        String user = principal.getName();

        profileService.SetData(user, data);
        return ResponseEntity.ok("User : "+user + " Phone : "+data.getPhone() + " DOB : "+data.getDob() + " Bio : "+data.getBio() + "Profile Pic No. : "+data.getPropic());
    }

    //Endpoint for Fetching Entire user profile row
    @GetMapping("/profile/getprofile")
    public ProfileFetchingDTO FetchProfile(Principal principal){
        String username = principal.getName();
        return profileService.FetchData(username);
    }

}
