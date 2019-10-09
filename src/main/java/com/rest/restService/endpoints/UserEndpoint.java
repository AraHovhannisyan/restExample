package com.rest.restService.endpoints;

import com.rest.restService.exception.UserNotFoundException;
import com.rest.restService.model.Post;
import com.rest.restService.model.User;
import com.rest.restService.repository.PostRepository;
import com.rest.restService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserEndpoint {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public UserEndpoint(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> users() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public Resource<User> user(@PathVariable Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            Resource<User> resource = new Resource<>(byId.get());
            ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).users());
            resource.add(linkTo.withRel("All-Users"));
            return resource;
        } else {
            throw new UserNotFoundException("id-" + id);
        }
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            userRepository.delete(byId.get());

        } else {
            throw new UserNotFoundException("Cannot delete user with id " + id + " , user doesnt exists");
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User save = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> getUserPosts(@PathVariable Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            return postRepository.findAllByUser(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}
