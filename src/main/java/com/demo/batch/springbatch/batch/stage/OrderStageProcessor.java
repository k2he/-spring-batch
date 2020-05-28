package com.demo.batch.springbatch.batch.stage;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.dto.OrderList.Order;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author kaihe
 *
 */

@Component
@RequiredArgsConstructor
public class OrderStageProcessor implements ItemProcessor<Order, OrderStage> {

  @NonNull
  private OrderService orderService;

  @Override
  public OrderStage process(Order order) throws Exception {
    return orderService.convertToOrderStage(order);
  }

}
