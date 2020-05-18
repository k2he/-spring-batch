package com.demo.batch.springbatch.service;

import java.util.List;
import com.demo.batch.springbatch.model.User;

/**
 * @author kaihe
 *
 */
public interface UserService {
  List<User> getAll();
}
