package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.Category;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }

    public List<Category> findAll(Status status){
        return categoryRepository.findAllByStatus(status);
    }
}
