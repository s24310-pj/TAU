package pl.pjatk.zjazd4.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String userId, String message) {
        System.out.println("Notification sent to user " + userId + ": " + message);
    }
}
