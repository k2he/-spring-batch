package com.demo.batch.springbatch.service;

import java.util.List;
import com.demo.batch.springbatch.model.Order;

/**
 * @author kaihe
 *
 */
public interface OrderService {
  List<Order> getAll();
}
