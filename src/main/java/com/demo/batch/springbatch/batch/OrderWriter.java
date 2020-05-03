/**
 * 
 */
package com.demo.batch.springbatch.batch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.repository.OrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kaihe
 *
 */
@Component
@RequiredArgsConstructor
public class OrderWriter implements ItemWriter<Order> {

  @NonNull
  private OrderRepository repo;
  
  @Override
  @Transactional
  public void write(List<? extends Order> orders) throws Exception {
    repo.saveAll(orders);
  }

}
