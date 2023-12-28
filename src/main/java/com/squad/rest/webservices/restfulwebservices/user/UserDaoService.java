package com.squad.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

  private final UserRepository userRepository;

  public UserDaoService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public User findOne(Integer id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  public void deleteById(Integer id) {
    userRepository.deleteById(id);
  }
}
