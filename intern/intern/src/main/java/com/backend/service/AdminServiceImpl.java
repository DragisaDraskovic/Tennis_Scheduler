package com.backend.service;

import com.backend.model.AdminEntity;
import com.backend.repository.AdminRepository;
import com.backend.service.serviceInterface.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminEntity getAdmin(String email){
        return adminRepository.findByEmail(email).get();
    }


}
