package com.backend.service;

import com.backend.exception.NotFoundAdminException;
import com.backend.model.AdminEntity;
import com.backend.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
@Transactional
public class UserDetailServiceImpl implements UserDetailsService{

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        AdminEntity admin = adminRepository.findByEmail(email).orElseThrow(() -> new NotFoundAdminException("Admin not found in the database"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(admin.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(), authorities);
    }
}
