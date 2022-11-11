package com.project_4.cookpad_api.api.client;

import com.project_4.cookpad_api.entity.Post;
import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.search.SearchBody;
import com.project_4.cookpad_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/post")
public class PostApi {
    @Autowired
    PostService postService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "status", defaultValue = "-1") int status,
            @RequestParam(name = "cateId", defaultValue = "-1") Long cateId,
            @RequestParam(name = "originId", defaultValue = "-1") Long originId,
            @RequestParam(name = "userPostId", defaultValue = "-1") Long userPostId,
            @RequestParam(name = "end", required = false) String end
    ) {
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(limit)
                .withSort(sort)
                .withCateId(cateId)
                .withStatus(status)
                .withOriginId(originId)
                .withUserPostId(userPostId)
                .withStart(start)
                .withEnd(end)
                .build();

        return ResponseEntity.ok(postService.findAll(searchBody));
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
        int like = 0;
        // map object
        updatePost.setName(post.getName());
        updatePost.setCategory(post.getCategory());
        updatePost.setUser(post.getUser());
        updatePost.setIngredient(post.getIngredient());
        updatePost.setMaking(post.getMaking());
        updatePost.setDetail(post.getDetail());
        updatePost.setThumbnails(post.getThumbnails());
        updatePost.setUserIdLikes(post.getUserIdLikes());
        updatePost.setLikes(like++);
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
