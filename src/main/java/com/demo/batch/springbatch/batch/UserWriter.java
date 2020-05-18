/**
 * 
 */
package com.demo.batch.springbatch.batch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.User;
import com.demo.batch.springbatch.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */
@Component
@RequiredArgsConstructor
public class UserWriter implements ItemWriter<User> {

  @NonNull
  private UserRepository repo;
  
  @Override
  @Transactional
  public void write(List<? extends User> users) throws Exception {
    repo.saveAll(users);
  }

}
