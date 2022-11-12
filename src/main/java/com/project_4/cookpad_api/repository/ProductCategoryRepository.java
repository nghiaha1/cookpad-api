package com.project_4.cookpad_api.repository;

import com.project_4.cookpad_api.entity.ProductCategory;
import com.project_4.cookpad_api.entity.myenum.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findAllByStatus(Status status);
}
