package com.backend.controller;


import com.backend.dto.Schedule;
import com.backend.exception.InvalidDatesException;
import com.backend.service.ScheduleServiceImpl;
import com.backend.service.serviceInterface.ScheduleService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Date;

import static com.backend.constants.CourtConstatns.DB_COURT_ID;
import static com.backend.constants.PlayerConstants.DB_PLAYER_ID;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleControllerTest {

    private static final String URL_PREFIX = "/schedule";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private ScheduleServiceImpl scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(scheduleController).build();
    }

    @Test
    public void testCreateSchedule() throws InvalidDatesException {
        try {
            Date startDate = getTomorrowDateWithHours(19,0);
            Date endDate = getTomorrowDateWithHours(20,0);
            Schedule request = Schedule.builder().courtId(DB_COURT_ID).playerId(DB_PLAYER_ID).startDate(startDate).endDate(endDate).build();
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String json = mapper.writeValueAsString(request);

            when(scheduleService.save(request)).thenReturn(request);

            mockMvc.perform(post(URL_PREFIX + "/delete"+DB_PLAYER_ID).contentType(contentType).content(json))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteSchedule() throws Exception {
        mockMvc.perform(delete(URL_PREFIX + "/delete/"+DB_PLAYER_ID))
                .andExpect(status().isOk());
    }

    private Date getTomorrowDateWithHours(int hours, int minutes){
        Calendar c = Calendar.getInstance();
        DateTime dateTime=new DateTime(new Date());
        c.set(dateTime.getYear(), dateTime.getMonthOfYear()-1, dateTime.getDayOfMonth()+1, hours, minutes, 0);
        return c.getTime();
    }
}
