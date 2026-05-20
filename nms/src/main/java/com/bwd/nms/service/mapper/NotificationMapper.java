package com.bwd.nms.service.mapper;

import com.bwd.nms.domain.Notification;
import com.bwd.nms.domain.Timeline;
import com.bwd.nms.domain.User;
import com.bwd.nms.repository.TimelineRepository;
import com.bwd.nms.repository.UserRepository;
import com.bwd.nms.security.SecurityUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NotificationMapper {

    private final TimelineRepository timelineRepository;

    public NotificationMapper(TimelineRepository timelineRepository) {
        this.timelineRepository = timelineRepository;
    }

    public Mono<Timeline> notificationToTimeline(Notification notification){
        Timeline timeline = new Timeline();

        return timelineRepository.findIdByLogin(notification.getLogin())
                .map(id -> {
                    timeline.setEventDate(notification.getEventdate());
                    timeline.setNewValue(notification.getNewvalue());
                    timeline.setOldValue(notification.getOldvalue());
                    timeline.setObject(notification.getFeature());
                    timeline.setOperation(notification.getOperation());
                    timeline.setUserId(id);

                    return timeline;
                });
    }
}

