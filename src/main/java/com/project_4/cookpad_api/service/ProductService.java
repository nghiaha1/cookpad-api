package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Page<Product> findAll(int page, int limit){
        return productRepository.findAll(PageRequest.of(page, limit));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> findAllByActive(int page, int limit){
        return productRepository.findAllByStatus(Status.ACTIVE, PageRequest.of(page, limit));
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public Optional<Product> findByIdActive(Long id){
        return productRepository.findByIdAndStatus(id, Status.ACTIVE);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }
}
