package com.habit_streak_tracker.habit_streak_tracker.Services;

import com.habit_streak_tracker.habit_streak_tracker.DTO.ProfileFetchingDTO;
import com.habit_streak_tracker.habit_streak_tracker.Model.ProfileEntity;
import com.habit_streak_tracker.habit_streak_tracker.Model.UsersEntity;
import com.habit_streak_tracker.habit_streak_tracker.Repository.ProfileEntryRepository;
import com.habit_streak_tracker.habit_streak_tracker.Repository.UserEntryRepository;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProfileService_Imp implements ProfileService{

    @Autowired
    private ProfileEntryRepository profileEntryRepository;

    @Autowired
    private UserEntryRepository userEntryRepository;

    //Creating new profile row for user to let user option to edit his profile.
    public String CreateProfileRow(String username) {
        UsersEntity users = userEntryRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Bo user found with name : " + username));
        try {
            ProfileEntity ProfileRow = new ProfileEntity();

            ProfileRow.setUser(users);

            profileEntryRepository.save(ProfileRow);

            return "Row creation done successfully";
        }
        catch(Exception e){
            System.out.println("Error : " + e.getMessage());
            return "Row creation failed";
        }
    }

    //Saving all new data for user profile.
    public Boolean SetData(String username, ProfileEntity profileData) {
        try {
            String nickname = profileData.getNickname();
            String phone = profileData.getPhone();
            LocalDate dob = profileData.getDob();
            String bio = profileData.getBio();
            Integer propic = profileData.getPropic();

            ProfileEntity existinguser = profileEntryRepository.findByUser_Username(username)
                    .orElseThrow(() -> new RuntimeException("User with username " + username + " doesn't exists"));

            existinguser.setNickname(nickname);
            existinguser.setPhone(phone);
            existinguser.setDob(dob);
            existinguser.setBio(bio);
            existinguser.setPropic(propic);

            profileEntryRepository.save(existinguser);

            return true;
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }

    public ProfileFetchingDTO FetchData(String username){
        //Will return entire row of the given user
        ProfileEntity profile = profileEntryRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("User with username : " + username + " doesn't exists"));

        return new ProfileFetchingDTO(
                profile.getId(),
                profile.getUser().getUsername(),
                profile.getNickname(),
                profile.getPhone(),
                profile.getDob(),
                profile.getBio(),
                profile.getPropic()
        );
    }
}
