package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class QueueMessage {

    private String playerEmail;

    private String courtName;

    private Date reservationDate;
}
