package com.bwd.nms.service;

import com.bwd.nms.domain.Feature;
import com.bwd.nms.domain.Notification;
import com.bwd.nms.domain.Timeline;
import com.bwd.nms.domain.User;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class NotificationService {

    @Autowired
    private TimelineService timelineService;

//    @Autowired
//    private MailService mailService;

    @Autowired
    private NotificationMapper notificationMapper;

//    @Autowired
//    private UserService userService;

    public Mono<Void> enabledNotification(Notification notification) {

        return notificationMapper.notificationToTimeline(notification)
            .flatMap(timelineService::save);
    }

    public Mono<Notification> createNotificationFromFeature(Object oldObject , Object newObject , String operation , Feature feature) {
        return SecurityUtils.getCurrentUserLogin()
            .map(login -> {
                Notification notification = new Notification();
                notification.setFeature(feature.toString());
                notification.setEventdate(Instant.now());
                notification.setOperation(operation);
                notification.setLogin(login);

                if (oldObject != null)
                    notification.setOldvalue(oldObject.toString());
                if (newObject != null)
                    notification.setNewvalue(newObject.toString());

                return notification;
            });
    }

}
