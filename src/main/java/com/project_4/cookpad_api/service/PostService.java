package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.Post;
import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.repository.PostRepository;
import com.project_4.cookpad_api.search.PostSpecification;
import com.project_4.cookpad_api.search.SearchBody;
import com.project_4.cookpad_api.search.SearchCriteria;
import com.project_4.cookpad_api.search.UserSpecification;
import com.project_4.cookpad_api.util.ConvertDateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.project_4.cookpad_api.search.SearchCriteriaOperator.*;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }

//    public List<Post> findAll() {
//        return postRepository.findAll();
//    }

    public Map<String, Object> findAll(SearchBody searchBody){
        Specification specification = Specification.where(null);

        if (searchBody.getStatus() > -1){
            specification = specification.and(new PostSpecification(new SearchCriteria("status", EQUALS, searchBody.getStatus())));
        }
        if (searchBody.getUserPostId() > -1){
            specification = specification.and(new PostSpecification(new SearchCriteria("user", EQUALS, searchBody.getUserPostId())));
        }
        if (searchBody.getCateId() > -1){
            specification = specification.and(new PostSpecification(new SearchCriteria("category", EQUALS, searchBody.getCateId())));
        }
        if (searchBody.getStart() != null && searchBody.getStart().length() > 0){
//            log.info("check start: " + orderSearchBody.getStart() );
//            log.info("Check Start begin" + searchBody.getStart());

            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getStart());
//            log.info("Check Start" + date);
//            log.info("check start convert date: " + date );
            specification = specification.and(new PostSpecification(new SearchCriteria("createdAt", GREATER_THAN_OR_EQUALS,date)));
        }
        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0){
            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getEnd());
            specification = specification.and(new PostSpecification(new SearchCriteria("createdAt", LESS_THAN_OR_EQUALS,date)));
        }

        Sort sort= Sort.by(Sort.Order.asc("id"));
        if (searchBody.getSort() !=null && searchBody.getSort().length() >0){
            if (searchBody.getSort().contains("desc")){
                sort = Sort.by(Sort.Order.desc("id"));
            }
        }
        Pageable pageable = PageRequest.of(searchBody.getPage() -1, searchBody.getLimit(),sort );
        Page<Post> postPage = postRepository.findAll(specification,pageable);
        List<Post> orderList = postPage.getContent();
        Map<String, Object> responses = new HashMap<>();
        responses.put("content",orderList);
        responses.put("currentPage",postPage.getNumber() + 1);
        responses.put("totalItems",postPage.getTotalElements());
        responses.put("totalPage",postPage.getTotalPages());
        return responses;
    }

    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }

    public void deleteById(Long id){
        postRepository.deleteById(id);
    }
}
