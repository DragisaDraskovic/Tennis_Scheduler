package com.backend.service.serviceInterface;

import com.backend.dto.Court;
import com.backend.exception.EntityAlreadyExistsException;
import com.backend.model.CourtEntity;

import java.util.List;

public interface CourtService {

    Integer save(Court dtoCourt);

    List<Court> getAllCourts();

    //void delete(Integer id) throws Exception;

    void delete(String name);
}
