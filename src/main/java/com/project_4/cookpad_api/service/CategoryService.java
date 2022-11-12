package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.ProductCategory;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.ProductCategoryRepository;
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
    ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> findAll(){
        return productCategoryRepository.findAll();
    }

    public Optional<ProductCategory> findById(Long id){
        return productCategoryRepository.findById(id);
    }

    public List<ProductCategory> findAll(Status status){
        return productCategoryRepository.findAllByStatus(status);
    }
}
