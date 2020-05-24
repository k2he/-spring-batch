package com.demo.batch.springbatch.batch.order;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@Component
@RequiredArgsConstructor
public class OrderProcessor implements ItemProcessor<OrderStage, Order> {

  @NonNull
  private OrderService orderService;

  @Override
  public Order process(OrderStage orderStage) throws Exception {
    return orderService.convertToOrder(orderStage);
  }

}
