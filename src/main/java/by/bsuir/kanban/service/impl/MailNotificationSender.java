package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.domain.Notification;
import by.bsuir.kanban.service.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * Created by ulza1116 on 5/2/2017.
 */
@Service
public class MailNotificationSender implements NotificationSender {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailNotificationSender(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendNotification(Notification notification) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(notification.getFrom());
        simpleMailMessage.setTo(notification.getTo());
        simpleMailMessage.setSubject(notification.getSubject());
        simpleMailMessage.setText(notification.getBody());

        javaMailSender.send(simpleMailMessage);
    }
}
