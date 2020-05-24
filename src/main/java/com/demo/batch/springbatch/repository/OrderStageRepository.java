/**
 * 
 */
package com.demo.batch.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.demo.batch.springbatch.model.OrderStage;

/**
 * @author kaihe
 *
 */
public interface OrderStageRepository extends JpaRepository<OrderStage, Long> {

  @Modifying
  @Query(value = "TRUNCATE TABLE orders_stage", nativeQuery = true)
  void truncate();
}
