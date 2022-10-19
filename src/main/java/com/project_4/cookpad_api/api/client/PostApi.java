package com.project_4.cookpad_api.api.client;

import com.project_4.cookpad_api.entity.Post;
import com.project_4.cookpad_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/post")
public class PostApi {
    @Autowired
    PostService postService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getList() {
        return ResponseEntity.ok(postService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Post> create(@RequestBody Post post) {
        return ResponseEntity.ok(postService.save(post));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post post) {
        Optional<Post> optionalPost = postService.findById(id);
        if (!optionalPost.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        Post updatePost = optionalPost.get();
        // map object
        updatePost.setName(post.getName());
        updatePost.setCategory(post.getCategory());
        updatePost.setUser(post.getUser());
        updatePost.setIngredient(post.getIngredient());
        updatePost.setMaking(post.getMaking());
        updatePost.setDescription(post.getDescription());
        updatePost.setDetail(post.getDetail());
        updatePost.setThumbnails(post.getThumbnails());
        updatePost.setLikes(post.getLikes());
        return ResponseEntity.ok(postService.save(updatePost));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!postService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        postService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
