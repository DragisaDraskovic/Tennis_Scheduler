package com.backend.service.serviceInterface;

import com.backend.dto.Player;
import com.backend.model.PlayerEntity;

import java.util.List;

public interface PlayerService {

    PlayerEntity save(Player dtoPlayer);

    List<Player> getAllPlayers();

    void delete(Integer id);
}
