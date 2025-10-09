package com.habit_streak_tracker.habit_streak_tracker.Services;

import com.habit_streak_tracker.habit_streak_tracker.Model.UsersEntity;
import com.habit_streak_tracker.habit_streak_tracker.Repository.UserEntryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class Users_Imp implements UsersServices, UserDetailsService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    @Transactional
    public Boolean register(UsersEntity entries){
        if (userEntryRepository.existsById(entries.getUsername())) {
            throw new IllegalStateException("Username is already taken.");
        }
    else {
            userEntryRepository.save(entries);
            return true;
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user = userEntryRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username+" user not found"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

}
