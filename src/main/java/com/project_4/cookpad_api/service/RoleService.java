package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.Role;
import com.project_4.cookpad_api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> findById(Long id){
        return roleRepository.findById(id);
    }
}
