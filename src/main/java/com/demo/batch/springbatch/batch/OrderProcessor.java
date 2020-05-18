package com.demo.batch.springbatch.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.model.Order;
import io.micrometer.core.instrument.util.StringUtils;

/**
 * @author kaihe
 *
 */
@Component
public class OrderProcessor implements ItemProcessor<Order, Order> {

  private static final String DEFAULT_NOTE = "Default Note";
  @Override
  public Order process(Order order) throws Exception {
    Order processedOrder = Order.builder()
        .orderRef(order.getOrderRef())
        .amount(order.getAmount())
        .note(StringUtils.isBlank(order.getNote()) ? DEFAULT_NOTE : order.getNote())
        .orderDate(order.getOrderDate())
        .build();
    
    return processedOrder;
  }
}
