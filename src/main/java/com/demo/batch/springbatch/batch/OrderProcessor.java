/**
 * 
 */
package com.demo.batch.springbatch.batch;

import java.time.Instant;
import java.time.ZoneId;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.model.Order;

/**
 * @author kaihe
 *
 */
@Component
public class OrderProcessor implements ItemProcessor<Order, Order> {

  @Override
  public Order process(Order order) throws Exception {
    Order processedOrder = Order.builder()
        .orderRef(order.getOrderRef())
        .amount(order.getAmount())
        .note(order.getNote())
        .orderDate(order.getOrderDate())
        .build();
    
    return processedOrder;
  }
}
