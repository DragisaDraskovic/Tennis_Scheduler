package com.backend.services;

import com.backend.repository.CourtRepository;
import com.backend.service.serviceInterface.CourtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourtServiceTest {

    @Autowired
    ModelMapper modelMapper;

    @Mock
    private CourtRepository courtRepository;

    @InjectMocks
    private CourtService courtService;

    @Test
    public void testFindAllCourt() throws Exception {
        //given
       courtService.getAllCourts();
        //then
        verify(courtRepository).findAll();

    }
}