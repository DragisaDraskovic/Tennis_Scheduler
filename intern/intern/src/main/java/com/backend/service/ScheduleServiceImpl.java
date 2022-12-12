package com.backend.service;

import com.backend.dto.Schedule;
import com.backend.exception.InvalidDatesException;
import com.backend.model.CourtEntity;
import com.backend.model.PlayerEntity;
import com.backend.model.ScheduleEntity;
import com.backend.repository.CourtRepository;
import com.backend.repository.PlayerRepository;
import com.backend.repository.ScheduleRepository;
import com.backend.service.serviceInterface.ScheduleService;
import com.backend.util.UtilService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final PlayerRepository playerRepository;

    private final CourtRepository courtRepository;

    private final ModelMapper modelMapper;

    private final AwsSQSServiceImpl sqsService;

    private final UtilService utilService;

    private static final int SATURDAY = 7;

    private static final int SUNDAY = 1;


    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, PlayerRepository playerRepository,
                               CourtRepository courtRepository, ModelMapper modelMapper, AwsSQSServiceImpl sqsService, UtilService utilService,
                               @Value("${schedule.minimum.duration.minutes}")Integer minDuration,@Value("${schedule.maximum.duration.minutes}") Integer maxDuration) {
        this.scheduleRepository = scheduleRepository;
        this.playerRepository = playerRepository;
        this.courtRepository = courtRepository;
        this.modelMapper = modelMapper;
        this.sqsService = sqsService;
        this.utilService = utilService;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    @Value("${schedule.minimum.duration.minutes}")
    private Integer minDuration;

    @Value("${schedule.maximum.duration.minutes}")
    private Integer maxDuration;


    public Schedule save(Schedule dto){
        ScheduleEntity schedule = createScheduleFromDto(dto);
        if(checkIfPlayerAlreadyHasSchedule(schedule)) {throw new InvalidDatesException("Player already made a reservation.");}
        if(!checkIfDatesValid(schedule)) {throw new InvalidDatesException("Input dates are invalid.");}
        if(moreThanFiveSlotsByPlayer(schedule)){
            utilService.payment();
        }
        sqsService.sendNotificationToQueue(schedule);
        ScheduleEntity entity= saveTransactional(schedule);
        return modelMapper.map(entity,Schedule.class);
    }


    public void delete(Integer id){
        scheduleRepository.deleteById(id);
    }

    ScheduleEntity saveTransactional(ScheduleEntity schedule){
        return scheduleRepository.save(schedule);
    }


    private boolean checkIfDatesValid(ScheduleEntity schedule){
        Date now = new Date();
        if(schedule.getStartDate().compareTo(schedule.getEndDate()) > 0 ||
                schedule.getStartDate().compareTo(now) < 0 ) return false;
        if(!checkCourtWorkingHours(schedule)) return false;
        if(!checkDurationOfSchedule(schedule))return false;
        return !(checkIfOverlapping(schedule));
    }

    private boolean moreThanFiveSlotsByPlayer(ScheduleEntity schedule){
        Date startDate = getDayWithSpecificHours(new Date(),1);
        //Date endDate = getDayWithSpecificHours(schedule.getEndDate(),23);
        List<ScheduleEntity> schedules =
                scheduleRepository.getByPlayer(startDate,schedule.getPlayer().getId());
        return schedules.size()>5;
    }

    private boolean checkIfOverlapping(ScheduleEntity schedule){
        Date startDate = getDayWithSpecificHours(schedule.getStartDate(),1);
        Date endDate = getDayWithSpecificHours(schedule.getEndDate(),23);
        List<ScheduleEntity> schedules = scheduleRepository.getByCourtBetweenDates(startDate,endDate,schedule.getCourt().getId());
        Optional<ScheduleEntity> result =schedules
                .stream()
                .parallel()
                .filter(dbSchedule -> schedule.getStartDate().compareTo(dbSchedule.getEndDate()) < 0 &&
                        dbSchedule.getEndDate().compareTo(dbSchedule.getStartDate()) > 0)
                .findAny();
        return result.isPresent();
    }

    private boolean checkIfPlayerAlreadyHasSchedule(ScheduleEntity schedule){
        Date startDate = getDayWithSpecificHours(schedule.getStartDate(),1);
        Date endDate = getDayWithSpecificHours(schedule.getEndDate(),23);
        List<ScheduleEntity> schedules = scheduleRepository.getByPlayerAndCourtBetweenDates(startDate,endDate,schedule.getPlayer().getId(),schedule.getCourt().getId());
        return schedules.size() > 0;
    }

    private boolean checkDurationOfSchedule(ScheduleEntity schedule){
        long diff = schedule.getEndDate().getTime() - schedule.getStartDate().getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return !(minutes >maxDuration || minutes < minDuration);
    }

    private boolean checkCourtWorkingHours(ScheduleEntity schedule) {
        Calendar c = Calendar.getInstance();
        c.setTime(schedule.getStartDate());
        int startDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int startHours= 18;
        int endHours =23;
        if(startDayOfWeek == SATURDAY || startDayOfWeek == SUNDAY){
            startHours=17;
            endHours = 22;
        }
        Date startDate = getDayWithSpecificHours(schedule.getStartDate(),startHours);
        Date endDate = getDayWithSpecificHours(schedule.getStartDate(),endHours);

        return startDate.before(schedule.getStartDate())  && endDate.after(schedule.getEndDate());
    }

    private Date getDayWithSpecificHours(Date date,int hours){
        Calendar c = Calendar.getInstance();
        DateTime dateTime=new DateTime(date);
        c.set(dateTime.getYear(), dateTime.getMonthOfYear()-1, dateTime.getDayOfMonth(), hours, 0, 0);
        return c.getTime();
    }

    private ScheduleEntity createScheduleFromDto(Schedule dto) {
        PlayerEntity player = playerRepository.findById(dto.getPlayerId()).get();
        CourtEntity court =  courtRepository.findById(dto.getCourtId()).get();
        ScheduleEntity schedule = ScheduleEntity
                .builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .player(player)
                .court(court)
                .build();
        return schedule;
    }
}