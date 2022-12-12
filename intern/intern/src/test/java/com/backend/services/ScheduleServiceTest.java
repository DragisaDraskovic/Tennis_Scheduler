package com.backend.services;


import com.backend.dto.Schedule;
import com.backend.exception.InvalidDatesException;
import com.backend.model.CourtEntity;
import com.backend.model.PlayerEntity;
import com.backend.repository.CourtRepository;
import com.backend.repository.PlayerRepository;
import com.backend.repository.ScheduleRepository;
import com.backend.service.AwsSQSServiceImpl;
import com.backend.service.ScheduleServiceImpl;
import com.backend.service.serviceInterface.ScheduleService;
import com.backend.util.UtilService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.backend.constants.PlayerConstants.*;
import static com.backend.constants.CourtConstatns.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CourtRepository courtRepository;

    @Mock
    private AwsSQSServiceImpl awsSQSService;

    @Mock
    private UtilService utilService;

    @Mock
    private ModelMapper modelMapper;

    private ScheduleService scheduleService;


    @Test(expected = InvalidDatesException.class)
    public void courtNotWorkingTime(){
        this.scheduleService = new ScheduleServiceImpl(scheduleRepository,playerRepository,courtRepository,modelMapper,awsSQSService,utilService,30,120);
        PlayerEntity player= PlayerEntity.builder().firstName(DB_FIRST_NAME).lastName(DB_LAST_NAME).email(DB_EMAIL).dateOfBirth(DB_DATE).id(DB_PLAYER_ID).build();
        CourtEntity court = CourtEntity.builder().name(DB_COURT_NAME).id(DB_COURT_ID).build();
        when(courtRepository.findById(DB_COURT_ID)).thenReturn(Optional.of(court));
        when(playerRepository.findById(DB_PLAYER_ID)).thenReturn(Optional.of(player));
        Date startDate = getTomorrowDateWithHours(12,0);
        Date endDate = getTomorrowDateWithHours(13,0);
        Schedule dto = Schedule.builder().courtId(DB_COURT_ID).playerId(DB_PLAYER_ID).startDate(startDate).endDate(endDate).build();
        scheduleService.save(dto);
    }

    @Test(expected = InvalidDatesException.class)
    public void scheduleToLong(){
        this.scheduleService = new ScheduleServiceImpl(scheduleRepository,playerRepository,courtRepository,modelMapper,awsSQSService, utilService, 30,120);        PlayerEntity player= PlayerEntity.builder().firstName(DB_FIRST_NAME).lastName(DB_LAST_NAME).email(DB_EMAIL).dateOfBirth(DB_DATE).id(DB_PLAYER_ID).build();
        CourtEntity court = CourtEntity.builder().name(DB_COURT_NAME).id(DB_COURT_ID).build();
        when(courtRepository.findById(DB_COURT_ID)).thenReturn(Optional.of(court));
        when(playerRepository.findById(DB_PLAYER_ID)).thenReturn(Optional.of(player));
        Date startDate = getTomorrowDateWithHours(19,0);
        Date endDate = getTomorrowDateWithHours(22,0);
        Schedule dto = Schedule.builder().courtId(DB_COURT_ID).playerId(DB_PLAYER_ID).startDate(startDate).endDate(endDate).build();
        scheduleService.save(dto);
    }

    private Date getTomorrowDateWithHours(int hours, int minutes){
        Calendar c = Calendar.getInstance();
        DateTime dateTime=new DateTime(new Date());
        c.set(dateTime.getYear(), dateTime.getMonthOfYear()-1, dateTime.getDayOfMonth()+1, hours, minutes, 0);
        return c.getTime();
    }
}
