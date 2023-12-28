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
@RequestMapping("users")
public class UserResource {

  private final UserDaoService service;
  public UserResource(UserDaoService service) {
    this.service = service;
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
    if(user == null) throw new UserNotFoundException("ID: " + id);

    WebMvcLinkBuilder allUsersLink = linkTo(methodOn(this.getClass()).retrieveAllUsers());

    return EntityModel
          .of(user)
          .add(allUsersLink.withRel("all-users"));
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

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable("id") Integer id) {
    service.deleteById(id);
  }

}
