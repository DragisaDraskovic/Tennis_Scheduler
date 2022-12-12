package com.backend.repository;

import com.backend.model.CourtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourtRepository extends JpaRepository<CourtEntity, Integer> {

    Optional<CourtEntity> findByName(String name);

    List<CourtEntity> findAll();

}
