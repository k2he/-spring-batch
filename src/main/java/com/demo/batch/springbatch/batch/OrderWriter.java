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
import com.demo.batch.springbatch.repository.OrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */
@Component
@RequiredArgsConstructor
public class OrderWriter implements ItemWriter<Order> {

  @NonNull
  private OrderRepository orderRepository;
  
  @Override
  @Transactional
  public void write(List<? extends Order> orders) throws Exception {
    orderRepository.saveAll(orders);
  }

}
