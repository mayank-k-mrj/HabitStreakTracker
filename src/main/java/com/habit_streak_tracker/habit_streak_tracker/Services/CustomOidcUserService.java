package com.habit_streak_tracker.habit_streak_tracker.Services;

import com.habit_streak_tracker.habit_streak_tracker.Model.UsersEntity;
import com.habit_streak_tracker.habit_streak_tracker.Repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // This line will 100% print to your console now.
        System.out.println("--- CustomOidcUserService.loadUser() IS EXECUTING ---");

        // 1. Get the OidcUser from Google
        OidcUser oidcUser = super.loadUser(userRequest);

        // 2. Get the Google ID (the 'sub' attribute) and email
        String googleUserId = oidcUser.getAttribute("sub");
        String email = oidcUser.getAttribute("email");

        // 3. Check if user exists by their Google ID (which we use as the username)
        Optional<UsersEntity> userOptional = userEntryRepository.findByUsername(googleUserId);

        if (userOptional.isEmpty()) {
            System.out.println("User not found by Google ID. Checking by email...");

            // 4. Check if they exist by email (in case of a previous manual signup)
            Optional<UsersEntity> userByEmail = userEntryRepository.findByEmail(email);

            if (userByEmail.isPresent()) {
                System.out.println("User found by email. This account needs to be linked.");
                // For now, we'll throw an error to make the problem visible
                // In a real app, you would link the 'sub' ID to the existing user.
                throw new OAuth2AuthenticationException("User with email " + email + " already exists. Account linking not implemented.");
            } else {
                // 5. This is a brand new Google user. Create them.
                System.out.println("This is a new user. Creating entry...");
                UsersEntity newUser = new UsersEntity();
                newUser.setEmail(email);

                // 6. Use the Google ID as the 'username' (which is your @Id)
                newUser.setUsername(googleUserId);

                // 7. Set a random, unusable password
                String randomPassword = UUID.randomUUID().toString();
                newUser.setPassword(passwordEncoder.encode(randomPassword));

                newUser.setEnabled(true);
                newUser.setAuthorities("ROLE_USER");

                userEntryRepository.save(newUser);
                userEntryRepository.flush(); // Force the save
                System.out.println("New user saved successfully: " + googleUserId);
            }
        } else {
            System.out.println("Returning existing user: " + googleUserId);
        }

        return oidcUser;
    }
}
