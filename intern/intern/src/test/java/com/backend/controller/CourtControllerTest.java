package com.backend.controller;

import com.backend.dto.Court;
import com.backend.dto.Player;
import com.backend.service.CourtServiceImpl;
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
import java.util.List;

import static com.backend.constants.CourtConstatns.DB_COURT_ID;
import static com.backend.constants.CourtConstatns.DB_COURT_NAME;
import static com.backend.constants.PlayerConstants.*;
import static com.backend.constants.PlayerConstants.DB_LAST_NAME;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourtControllerTest {

    private static final String URL_PREFIX = "/court";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private CourtServiceImpl courtService;

    @InjectMocks
    private CourtController courtController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(courtController).build();
    }

    @Test
    public void testGetAllCourts() throws Exception {
        Court dto = Court.builder().name(DB_COURT_NAME).build();
        List<Court> courts = new ArrayList<>();
        courts.add(dto);
        when(courtService.getAllCourts()).thenReturn(courts);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = mapper.writeValueAsString(courts);
        mockMvc.perform(get(URL_PREFIX + "/getAllCourts").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DB_COURT_NAME)));
    }

    @Test
    public void testDeleteCourt() throws Exception {
        mockMvc.perform(delete(URL_PREFIX + "/delete/"+DB_COURT_ID))
                .andExpect(status().isOk());
    }
}
