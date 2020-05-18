package com.demo.batch.springbatch.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.demo.batch.springbatch.model.User;
import com.demo.batch.springbatch.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  
  @NonNull
  private UserRepository userRespository;
  
  public List<User> getAll() {
    return userRespository.findAll();
  }
}
