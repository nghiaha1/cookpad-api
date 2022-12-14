package com.project_4.cookpad_api.repository;

import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.myenum.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByIdAndStatus(Long id, Status status);

    Page<Product> findAllByStatus(Status status, Pageable pageable);

    List<Product> findAllByStatus(Status status);
}
