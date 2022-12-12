package com.backend.controller;

import com.backend.dto.Court;
import com.backend.model.CourtEntity;
import com.backend.service.CourtServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@ControllerAdvice
@RequestMapping(value = "/court")
public class CourtController {

    private final CourtServiceImpl courtService;



    @PostMapping("/saveCourt")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer saveCourt(@RequestBody Court dtoCourt){
            return courtService.save(dtoCourt);
    }


    @GetMapping(value = "/getAllCourts")
    @ResponseStatus(HttpStatus.OK)
    public List<Court> getAllCourts(){
            return courtService.getAllCourts();
    }

    @DeleteMapping(value = "/delete/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCourt(@PathVariable String name){
           courtService.delete(name);
    }
}
