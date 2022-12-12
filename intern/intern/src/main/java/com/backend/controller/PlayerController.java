package com.backend.controller;

import com.backend.dto.Player;
import com.backend.model.PlayerEntity;
import com.backend.service.PlayerServiceImpl;
import com.backend.util.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@ControllerAdvice
@RequestMapping(value = "/player")
public class PlayerController {

    private final PlayerServiceImpl playerService;

    private UtilService utilService;


    @PostMapping(value = "/savePlayer")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer savePlayer(@RequestBody Player dtoPlayer){
            PlayerEntity player = playerService.save(dtoPlayer);
            return player.getId();
    }


    @GetMapping(value = "/getAllPlayers")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getAllPlayers(){
            return playerService.getAllPlayers();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlayer(@PathVariable Integer id) {
            playerService.delete(id);
    }



}
