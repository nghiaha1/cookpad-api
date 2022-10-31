package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.ProductRepository;
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

        if (searchBody.getNameProduct() != null && searchBody.getNameProduct().length() > 0 ){
            specification = specification.and(new UserSpecification(new SearchCriteria("name", EQUALS, searchBody.getNameProduct())));
        }
        if (searchBody.getPrice() != null && searchBody.getPrice().length() > 0 ){
            specification = specification.and(new UserSpecification(new SearchCriteria("price", EQUALS, searchBody.getPrice())));
        }
        if (searchBody.getStart() != null && searchBody.getStart().length() > 0){
//            log.info("check start: " + orderSearchBody.getStart() );
//            log.info("Check Start begin" + searchBody.getStart());

            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getStart());
//            log.info("Check Start" + date);
//            log.info("check start convert date: " + date );
            specification = specification.and(new UserSpecification(new SearchCriteria("createdAt", GREATER_THAN_OR_EQUALS,date)));
        }
        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0){
            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getEnd());
            specification = specification.and(new UserSpecification(new SearchCriteria("createdAt", LESS_THAN_OR_EQUALS,date)));
        }

        Sort sort= Sort.by(Sort.Order.asc("id"));
        if (searchBody.getSort() !=null && searchBody.getSort().length() >0){
            if (searchBody.getSort().contains("desc")){
                sort = Sort.by(Sort.Order.desc("id"));
            }
        }
        Pageable pageable = PageRequest.of(searchBody.getPage() -1, searchBody.getLimit(),sort );
        Page<User> pageUser = productRepository.findAll(specification,pageable);
        List<User> orderList = pageUser.getContent();
        Map<String, Object> responses = new HashMap<>();
        responses.put("content",orderList);
        responses.put("currentPage",pageUser.getNumber() + 1);
        responses.put("totalItems",pageUser.getTotalElements());
        responses.put("totalPage",pageUser.getTotalPages());
        return responses;
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
