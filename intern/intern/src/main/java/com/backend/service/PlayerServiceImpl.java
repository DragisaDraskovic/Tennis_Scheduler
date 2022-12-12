package com.backend.service;

import com.backend.dto.Player;
import com.backend.exception.EntityAlreadyExistsException;
import com.backend.exception.EntityDoesNotExistsException;
import com.backend.exception.NotFoundPlayerException;
import com.backend.model.PlayerEntity;
import com.backend.repository.PlayerRepository;
import com.backend.service.serviceInterface.PlayerService;
import com.backend.util.UtilService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    private final UtilService utilService;

    private final ModelMapper modelMapper;

    public PlayerEntity save(Player dtoPlayer){
        playerRepository.findByEmail(dtoPlayer.getEmail()).ifPresent(s -> {throw new EntityAlreadyExistsException("Player with this email already exists");});
        PlayerEntity player = modelMapper.map(dtoPlayer, PlayerEntity.class);
        utilService.createPlayerAcc(player.getEmail(),player.getFirstName(),player.getLastName());
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        List<PlayerEntity> players = playerRepository.findAll();
        List<Player> dtoPlayers = new ArrayList<>();
        players.forEach((player -> {
            dtoPlayers.add(modelMapper.map(player, Player.class));
        }));

        return dtoPlayers;
    }

    public void delete(Integer id){
        playerRepository.findById(id).orElseThrow(() -> new EntityDoesNotExistsException("Player with this id doesn't exist"));
        playerRepository.deleteById(id);
    }

}
