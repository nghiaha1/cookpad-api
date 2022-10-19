package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.Post;
import com.project_4.cookpad_api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }

    public void deleteById(Long id){
        postRepository.deleteById(id);
    }
}
