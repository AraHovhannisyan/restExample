package com.rest.restService.endpoints;

import com.rest.restService.model.Post;
import com.rest.restService.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PostEndpoint {

    private final PostRepository postRepository;

    public PostEndpoint(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> addPost(@Valid @RequestBody Post post) {
        @Valid Post save = postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }


}
