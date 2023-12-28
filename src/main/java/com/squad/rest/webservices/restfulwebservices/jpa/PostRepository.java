package com.squad.rest.webservices.restfulwebservices.jpa;

import com.squad.rest.webservices.restfulwebservices.user.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
