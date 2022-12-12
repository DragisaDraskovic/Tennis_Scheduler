package com.backend.service;

import com.backend.dto.QueueMessage;
import com.backend.model.ScheduleEntity;
import com.backend.service.serviceInterface.AwsSQSService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class AwsSQSServiceImpl implements AwsSQSService {

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;


    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    public void sendNotificationToQueue(ScheduleEntity schedule){
        QueueMessage queueMessage = QueueMessage.builder()
                .courtName(schedule.getCourt().getName())
                .playerEmail(schedule.getPlayer().getFirstName()+schedule.getPlayer().getLastName())
                .reservationDate(schedule.getStartDate())
                .build();
        queueMessagingTemplate.convertAndSend(endpoint,queueMessage);
    }
}
