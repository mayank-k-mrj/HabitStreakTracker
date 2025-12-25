package com.habit_streak_tracker.habit_streak_tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class HabitStreakTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitStreakTrackerApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(Environment env) {
		return args -> {
			System.out.println("--- DEBUG ENVIRONMENT ---");
			System.out.println("MYSQLHOST: " + env.getProperty("MYSQLHOST"));
			System.out.println("PORT: " + env.getProperty("PORT"));
			// Don't print passwords for security!
			System.out.println("-------------------------");
		};
	}

}
