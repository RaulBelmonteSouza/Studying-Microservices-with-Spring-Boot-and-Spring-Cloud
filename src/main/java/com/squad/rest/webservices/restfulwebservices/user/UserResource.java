package com.squad.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("users")
public class UserResource {

  private final UserDaoService service;

  public UserResource(UserDaoService service) {
    this.service = service;
  }

  @GetMapping
  public List<User> retrieveAllUsers() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public User retrieveUser(@PathVariable Integer id) {
    User user = service.findOne(id);
    if(user == null) throw new UserNotFoundException("ID: " + id);
    return user;
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

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable("id") Integer id) {
    service.deleteById(id);
  }

}
