package com.backend.services;

import com.backend.repository.PlayerRepository;
import com.backend.dto.Player;
import com.backend.model.PlayerEntity;
import com.backend.repository.PlayerRepository;
import com.backend.service.serviceInterface.PlayerService;
import com.backend.dto.Player;
import com.backend.model.PlayerEntity;
import com.backend.repository.PlayerRepository;
import com.backend.service.serviceInterface.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.backend.constants.PlayerConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerServicesTest {

    @Mock
    ModelMapper modelMapper;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void savePlayer() throws Exception {
        Player dto = Player.builder().firstName(DB_FIRST_NAME).lastName(DB_LAST_NAME).email(DB_EMAIL).dateOfBirth(DB_DATE).build();
        if (playerRepository.findByEmail(dto.getEmail()) == null) {
            playerService.save(dto);
        }
    }

    @Test
    public void getAllPlayers() throws Exception {
        List<PlayerEntity> list = playerRepository.findAll();
        List<Player> dto = new ArrayList<>();
        if (!list.isEmpty()) {
            for (PlayerEntity player : list) {
                dto.add(modelMapper.map(player, Player.class));
            }
            playerService.getAllPlayers();
        }
    }

    @Test
    public void deletePlayers() throws Exception {
        PlayerEntity player = PlayerEntity.builder().firstName(DB_FIRST_NAME).lastName(DB_LAST_NAME).email(DB_EMAIL).dateOfBirth(DB_DATE).build();
        if (playerRepository.findByEmail(player.getEmail()) != null) {
            playerService.delete(player.getId());
        }
    }
}


