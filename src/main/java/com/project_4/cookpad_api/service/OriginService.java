package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.Origin;
import com.project_4.cookpad_api.repository.OriginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OriginService {

    @Autowired
    OriginRepository originRepository;

    public Optional<Origin> findById(Long id){
        return originRepository.findById(id);
    }
}
