package com.backend.service.serviceInterface;

import com.backend.dto.Schedule;
import com.backend.model.ScheduleEntity;

public interface AwsSQSService {

    void sendNotificationToQueue(ScheduleEntity schedule);
}
