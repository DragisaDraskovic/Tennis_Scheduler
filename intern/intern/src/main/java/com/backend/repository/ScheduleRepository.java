package com.backend.repository;

import com.backend.model.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {

    @Override
    ScheduleEntity save(ScheduleEntity schedule);

    @Query("SELECT s from ScheduleEntity s where s.startDate >= ?1 and s.endDate <= ?2 and s.court.id = ?3")
    List<ScheduleEntity> getByCourtBetweenDates(Date startDate, Date endDate, Integer courtId);

    @Query("SELECT s from ScheduleEntity s where s.startDate >= ?1 and s.endDate <= ?2 and s.player.id = ?3")
    List<ScheduleEntity> getByPlayerBetweenDates(Date startDate, Date endDate, Integer playerId);

    @Query("SELECT s from ScheduleEntity s where s.startDate >= ?1 and s.player.id = ?2")
    List<ScheduleEntity> getByPlayer(Date startDate, Integer playerId);

    @Query("SELECT s from ScheduleEntity s where s.startDate >= ?1 and s.endDate <= ?2 and s.player.id = ?3 and s.court.id =?4")
    List<ScheduleEntity> getByPlayerAndCourtBetweenDates(Date startDate, Date endDate, Integer playerId, Integer courtId);
}
