package com.demo.batch.springbatch.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.repository.OrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  
  @NonNull
  private OrderRepository orderRespository;
  
  public List<Order> getAll() {
    return orderRespository.findAll();
  }
}
