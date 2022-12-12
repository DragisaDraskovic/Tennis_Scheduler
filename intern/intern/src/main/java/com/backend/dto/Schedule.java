package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Schedule {

    private Date startDate;

    private Date endDate;

    @NotNull(message = "Player is required")
    private Integer playerId;

    @NotNull(message = "Court is required")
    private Integer courtId;
}
