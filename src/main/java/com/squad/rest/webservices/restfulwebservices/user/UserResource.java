package com.squad.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("users")
public class UserResource {

  private UserDaoService service;

  public UserResource(UserDaoService service) {
    this.service = service;
  }

  @GetMapping
  public List<User> retrieveAllUsers() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public User retrieveUser(@PathVariable Integer id) {
    return service.findOne(id);
  }

  @PostMapping
  public ResponseEntity<User> saveUser(@RequestBody User user) {

    User userSaved = service.save(user);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(userSaved.getId())
        .toUri();

    return ResponseEntity.created(location).body(userSaved);
  }

}
