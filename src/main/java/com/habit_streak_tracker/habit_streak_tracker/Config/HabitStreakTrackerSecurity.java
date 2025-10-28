package com.habit_streak_tracker.habit_streak_tracker.Config;

import com.habit_streak_tracker.habit_streak_tracker.Services.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class HabitStreakTrackerSecurity {

    @Autowired
    private CustomOidcUserService customOidcUserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/hst/signup", "/signin.html", "signup.html", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        // 1. Specify the URL of your custom login page
                        .loginPage("/signin.html")
                        // 2. Specify the URL where the login form will be POSTed to
                        .loginProcessingUrl("/login")
                        // 3. Specify the page to go to after a successful login
                        .defaultSuccessUrl("/index.html", true)
                        // 4. Specify the page to go to after a failed login
                        .failureUrl("/signin.html?error=true"))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/signin.html") // Direct user to login page
                        .defaultSuccessUrl("/index.html", true) // On success, go to index.html
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOidcUserService) // Tell Spring to use your service to save the user
                        )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true) // Kills the session
                        .deleteCookies("JSESSIONID") // Deletes the cookie
                );
        return http.build();
    }

}
