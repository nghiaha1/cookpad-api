package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.ProductRepository;
import com.project_4.cookpad_api.search.ProductSpecification;
import com.project_4.cookpad_api.search.SearchBody;
import com.project_4.cookpad_api.search.SearchCriteria;
import com.project_4.cookpad_api.search.UserSpecification;
import com.project_4.cookpad_api.util.ConvertDateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.project_4.cookpad_api.search.SearchCriteriaOperator.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Page<Product> findAlls(int page, int limit){
        return productRepository.findAll(PageRequest.of(page, limit));
    }

    public Map<String, Object> findAll(SearchBody searchBody){
        Specification specification = Specification.where(null);

        if (searchBody.getCateId() > 0){
            specification = specification.and(new ProductSpecification(new SearchCriteria("category", EQUALS, searchBody.getCateId())));
        }
        if (searchBody.getStatus() > -1){
            specification = specification.and(new ProductSpecification(new SearchCriteria("status", EQUALS, searchBody.getStatus())));
        }
        if (searchBody.getNameProduct() != null && searchBody.getNameProduct().length() > 0 ){
            specification = specification.and(new ProductSpecification(new SearchCriteria("name", LIKE, "%" + searchBody.getNameProduct() + "%")));
        }
        if (searchBody.getStartPrice() != null && searchBody.getStartPrice().length() > 0){
            BigDecimal startPrice = new BigDecimal(searchBody.getStartPrice());
            specification = specification.and(new ProductSpecification(new SearchCriteria("price", GREATER_THAN_OR_EQUALS,startPrice)));
        }
        if (searchBody.getEndPrice() != null && searchBody.getEndPrice().length() > 0){
            BigDecimal endPrice = new BigDecimal(searchBody.getEndPrice());
            specification = specification.and(new ProductSpecification(new SearchCriteria("price", LESS_THAN_OR_EQUALS,endPrice)));
        }

        Sort sort= Sort.by(Sort.Order.asc("id"));
        if (searchBody.getSort() !=null && searchBody.getSort().length() >0){
            if (searchBody.getSort().contains("desc")){
                sort = Sort.by(Sort.Order.desc("id"));
            }
        }
        Pageable pageable = PageRequest.of(searchBody.getPage() -1, searchBody.getLimit(),sort );
        Page<Product> productPage = productRepository.findAll(specification,pageable);
        List<Product> orderList = productPage.getContent();
        Map<String, Object> responses = new HashMap<>();
        responses.put("content",orderList);
        responses.put("currentPage",productPage.getNumber() + 1);
        responses.put("totalItems",productPage.getTotalElements());
        responses.put("totalPage",productPage.getTotalPages());
        return responses;
    }

    public int totalProduct(){
        return productRepository.findAll().size();
    }

    public int totalProductByStatus(int status){
        Status status1 = Status.ACTIVE;
        if (status == 1){
            status1 = Status.ACTIVE;
        }if (status == 0){
            status1 = Status.INACTIVE;
        }if (status == 6){
            status1 = Status.SOLDOUT;
        }
        return productRepository.findAllByStatus(status1).size();
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
