package com.backend.repository;

import com.backend.model.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity,Integer> {

    Optional<PlayerEntity> findByEmail(String email);

    PlayerEntity deleteByEmail(String email);

    @Override
    void deleteById(Integer integer);
}
