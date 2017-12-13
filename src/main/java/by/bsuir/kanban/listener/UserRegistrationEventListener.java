package by.bsuir.kanban.listener;

import by.bsuir.kanban.domain.Notification;
import by.bsuir.kanban.event.UserRegistrationEvent;
import by.bsuir.kanban.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegistrationEventListener implements ApplicationListener<UserRegistrationEvent> {

    private final NotificationSender notificationSender;

    @Async
    @Override
    public void onApplicationEvent(UserRegistrationEvent userRegistrationEvent) {
        Notification notification = new Notification("vladislav.zavadski@gmail.com",
                userRegistrationEvent.getUserDTO().getEmail(), "Registration", "Body");

        notificationSender.sendNotification(notification);
    }
}
