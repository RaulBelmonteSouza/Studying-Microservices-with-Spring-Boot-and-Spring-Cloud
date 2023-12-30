package com.squad.rest.webservices.restfulwebservices.user;

import com.squad.rest.webservices.restfulwebservices.jpa.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  public void deleteById(Integer id) {
    userRepository.deleteById(id);
  }

  public List<Post> retrieveUserPosts(Integer userId) {
    User user = findOne(userId);
    return user.getPosts();
  }
}
