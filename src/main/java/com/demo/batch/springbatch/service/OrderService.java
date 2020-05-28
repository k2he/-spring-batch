package com.demo.batch.springbatch.service;

import java.util.List;
import com.demo.batch.springbatch.dto.OrderList;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.OrderStage;

/**
 * @author kaihe
 *
 */
public interface OrderService {

  List<Order> getAll();

  boolean validateXml();

  OrderStage convertToOrderStage(OrderList.Order orderObj) throws Exception;

}
