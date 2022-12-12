package com.backend.service.serviceInterface;

import com.backend.dto.Schedule;
import com.backend.model.ScheduleEntity;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;


public interface ScheduleService {

    Schedule save(Schedule dto) throws UnsupportedOperationException, PessimisticLockingFailureException;

    void delete(Integer id);


}
