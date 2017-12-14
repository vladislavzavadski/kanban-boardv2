package by.bsuir.kanban.service;

import by.bsuir.kanban.domain.Notification;

/**
 * Created by ulza1116 on 5/2/2017.
 */
public interface NotificationSender {
    void sendNotification(Notification notification);
}
