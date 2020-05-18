package com.demo.batch.springbatch.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.model.User;

/**
 * @author kaihe
 *
 */
@Component
public class UserProcessor implements ItemProcessor<User, User> {

  @Override
  public User process(User user) throws Exception {
    User processedUser = User.builder()
        .name(user.getName()).build();
    
    return processedUser;
  }
}
