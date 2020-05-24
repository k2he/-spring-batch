package com.demo.batch.springbatch.batch.stage;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author kaihe
 *
 */

@Component
@RequiredArgsConstructor
public class OrderStageProcessor implements ItemProcessor<JsonNode, OrderStage> {

  @NonNull
  private OrderService orderService;

  @Override
  public OrderStage process(JsonNode orderJson) throws Exception {
    return orderService.convertToOrderStage(orderJson);
  }

}
