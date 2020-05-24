package com.demo.batch.springbatch.service;

import java.util.List;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.OrderHistory;
import com.demo.batch.springbatch.model.OrderStage;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author kaihe
 *
 */
public interface OrderService {
  
  List<Order> getAll();
  
  boolean validateXml();
  
  OrderStage convertToOrderStage(JsonNode orderJson);
  
  Order convertToOrder(OrderStage orderStage);
  
  OrderHistory convertToOrderHist(Order order);
  
}
