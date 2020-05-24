package com.demo.batch.springbatch.batch.order;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.model.Order;
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
  public void write(List<? extends Order> items) throws Exception {
    orderRepository.saveAll(items);
  }
}
