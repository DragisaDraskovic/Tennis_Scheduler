package com.backend.config;

import com.backend.dto.Player;
import com.backend.dto.Schedule;
import com.backend.model.ScheduleEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper=new ModelMapper();
        TypeMap<ScheduleEntity, Schedule> propertyMapper = mapper.createTypeMap(ScheduleEntity.class, Schedule.class);
        propertyMapper.addMappings(
                m ->{ m.map(src -> src.getCourt().getId(), Schedule::setCourtId);
                      m.map(src -> src.getPlayer().getId(), Schedule::setPlayerId);
                }
        );
        return mapper;
    }
}