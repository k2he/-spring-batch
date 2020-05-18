/**
 * 
 */
package com.demo.batch.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.demo.batch.springbatch.model.Order;

/**
 * @author kaihe
 *
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Modifying
  @Query(value = "TRUNCATE TABLE orders", nativeQuery = true)
  void truncate();
}
