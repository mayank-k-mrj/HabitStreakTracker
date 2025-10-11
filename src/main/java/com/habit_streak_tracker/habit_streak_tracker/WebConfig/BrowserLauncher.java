package com.habit_streak_tracker.habit_streak_tracker.WebConfig;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.net.URI;

@Component
public class BrowserLauncher {

    // You can make this configurable via application.properties if you want
    private final String url = "http://localhost:8080/index.html";

    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser() {
        System.out.println("--- Spring application ready. Attempting to launch browser. ---");

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("--- Browser launched successfully. ---");
            } catch (Exception e) {
                System.err.println("Error launching browser: " + e.getMessage());
                printManualOpenMessage();
            }
        } else {
            System.out.println("Desktop not supported. Cannot launch browser automatically.");
            printManualOpenMessage();
        }
    }

    private void printManualOpenMessage() {
        System.out.println("--- Please open your browser manually to: " + url);
    }
}
