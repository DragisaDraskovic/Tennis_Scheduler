package com.backend.service;

import com.backend.dto.Court;
import com.backend.exception.EntityAlreadyExistsException;
import com.backend.exception.EntityDoesNotExistsException;
import com.backend.exception.NotFoundCourtException;
import com.backend.model.CourtEntity;
import com.backend.repository.CourtRepository;
import com.backend.service.serviceInterface.CourtService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;
    private final ModelMapper modelMapper;

    public Integer save(Court dtoCourt){
       courtRepository.findByName(dtoCourt.getName()).ifPresent(s -> {throw new EntityAlreadyExistsException("Court with name: " +dtoCourt.getName() + " already exists.");});
        CourtEntity court = modelMapper.map(dtoCourt, CourtEntity.class);
        return courtRepository.save(court).getId();
    }

    @GetMapping(value = "/getAll")
    public List<Court> getAllCourts() {
        List<CourtEntity> courts = courtRepository.findAll();
        List<Court> dtoCourts = new ArrayList<>();
        courts.forEach((court -> {
            dtoCourts.add(modelMapper.map(court, Court.class));
        }));

        return dtoCourts;
    }

    @Override
    public void delete(String name) {
        CourtEntity court = courtRepository.findByName(name).orElseThrow(() -> new EntityDoesNotExistsException("Court with name: " + name + "not found"));
        courtRepository.delete(court);
    }

}
