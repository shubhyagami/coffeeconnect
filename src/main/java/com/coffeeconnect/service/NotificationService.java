package com.coffeeconnect.service;

import com.coffeeconnect.entity.Notification;
import com.coffeeconnect.entity.User;
import com.coffeeconnect.enums.NotificationType;
import com.coffeeconnect.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getUserNotifications(User user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Page<Notification> getUserNotificationsPaginated(User user, int page, int size) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user, PageRequest.of(page, size));
    }

    public long getUnreadCount(User user) {
        return notificationRepository.countUnreadByUser(user);
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }

    @Transactional
    public void markAllAsRead(User user) {
        notificationRepository.findByUserOrderByCreatedAtDesc(user).forEach(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }

    @Transactional
    public Notification createNotification(User user, NotificationType type, String title, String message, String targetUrl) {
        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .title(title)
                .message(message)
                .targetUrl(targetUrl)
                .build();
        return notificationRepository.save(notification);
    }
}
