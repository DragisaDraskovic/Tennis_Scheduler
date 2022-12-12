package com.backend.repository;

import com.backend.model.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity,Integer> {
    Optional<AdminEntity> findByEmail(String email);

}
