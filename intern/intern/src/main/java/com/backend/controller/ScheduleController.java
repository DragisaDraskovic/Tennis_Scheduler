package com.backend.controller;

import com.backend.dto.Schedule;
import com.backend.model.ScheduleEntity;
import com.backend.service.ScheduleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@ControllerAdvice
@RequestMapping(value = "/schedule")
public class ScheduleController {

    private final ScheduleServiceImpl scheduleService;


    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule save(@Valid @RequestBody Schedule dto){
        return scheduleService.save(dto);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id){
        scheduleService.delete(id);
    }
}