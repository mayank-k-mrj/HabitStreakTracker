package com.habit_streak_tracker.habit_streak_tracker.Cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthcheck {

    @GetMapping("/health-check")
    public String health(){
        return "It is working";
    }
}
