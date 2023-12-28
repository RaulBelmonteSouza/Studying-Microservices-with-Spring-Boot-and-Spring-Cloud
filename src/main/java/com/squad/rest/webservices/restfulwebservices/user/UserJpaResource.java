package com.squad.rest.webservices.restfulwebservices.user;

import com.squad.rest.webservices.restfulwebservices.jpa.PostRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa/users")
public class UserJpaResource {

  private final UserDaoService service;
  private final PostRepository postRepository;

  public UserJpaResource(UserDaoService service, PostRepository postRepository) {
    this.service = service;
    this.postRepository = postRepository;
  }

  @GetMapping
  public CollectionModel<EntityModel<User>> retrieveAllUsers() {
    List<EntityModel<User>> allUsers = service.findAll()
            .stream()
            .map(user -> EntityModel.of(user)
                    .add(linkTo(methodOn(this.getClass())
                            .retrieveUser(user.getId()))
                            .withSelfRel()))
            .collect(Collectors.toList());

    return CollectionModel.of(allUsers);
  }

  @GetMapping("/{id}")
  public EntityModel<User> retrieveUser(@PathVariable Integer id) {
    User user = service.findOne(id);

    WebMvcLinkBuilder allUsersLink = linkTo(methodOn(this.getClass()).retrieveAllUsers());

    return EntityModel
          .of(user)
          .add(allUsersLink.withRel("all-users"));
  }

  @GetMapping("/{id}/posts")
  public List<Post> retrieveUserPosts(@PathVariable("id") Integer userId) {
    return service.retrieveUserPosts(userId);
  }

  @PostMapping
  public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {

    User userSaved = service.save(user);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(userSaved.getId())
        .toUri();

    return ResponseEntity.created(location).body(userSaved);
  }

  @PostMapping("/{id}/posts")
  public ResponseEntity<Post> saveUserPost(@PathVariable("id") Integer userId, @Valid @RequestBody Post post) {
    User user = service.findOne(userId);
    post.setUser(user);
    Post postSaved = postRepository.save(post);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(postSaved.getId())
            .toUri();
    return ResponseEntity.created(location).body(postSaved);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable("id") Integer id) {
    service.deleteById(id);
  }

}
