package ru.job4j.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format("subject = Notification %s to email %s.%n",
                    user.username, user.email);
            String body = String.format("body = Add a new event to %s", user.username);

            send(subject, body, user.email);
        });
    }

    public void send(String subject, String body, String email) {
    }

    public void close() {
        pool.shutdown();
        while (pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public record User(String username, String email) {
    }
}
