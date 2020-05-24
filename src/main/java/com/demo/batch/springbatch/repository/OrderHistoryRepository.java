/**
 * 
 */
package com.demo.batch.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.batch.springbatch.model.OrderHistory;

/**
 * @author kaihe
 *
 */
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

}
