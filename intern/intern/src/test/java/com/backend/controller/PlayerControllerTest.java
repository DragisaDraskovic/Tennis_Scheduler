package com.backend.controller;

import com.backend.dto.Player;
import com.backend.dto.Schedule;
import com.backend.exception.InvalidDatesException;
import com.backend.service.PlayerServiceImpl;
import com.backend.service.ScheduleServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.backend.constants.CourtConstatns.DB_COURT_ID;
import static com.backend.constants.PlayerConstants.*;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerControllerTest {

    private static final String URL_PREFIX = "/player";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private PlayerServiceImpl playerService;

    @InjectMocks
    private PlayerController playerController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }


    @Test
    public void testGetAllPlayers() throws Exception {
        Player dto = Player.builder().firstName(DB_FIRST_NAME).lastName(DB_LAST_NAME).email(DB_EMAIL).dateOfBirth(DB_DATE).build();
        List<Player> players = new ArrayList<>();
        players.add(dto);
        when(playerService.getAllPlayers()).thenReturn(players);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = mapper.writeValueAsString(players);
        mockMvc.perform(get(URL_PREFIX + "/getAllPlayers").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DB_FIRST_NAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DB_LAST_NAME)));
    }

    @Test
    public void testDeletePlayer() throws Exception {
        mockMvc.perform(delete(URL_PREFIX + "/delete/"+DB_PLAYER_ID))
                .andExpect(status().isOk());
    }

}
