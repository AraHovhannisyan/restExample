package com.rest.restService.repository;

import com.rest.restService.model.Post;
import com.rest.restService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);
}
