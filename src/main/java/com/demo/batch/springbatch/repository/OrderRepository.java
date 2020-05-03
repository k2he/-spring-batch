/**
 * 
 */
package com.demo.batch.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.batch.springbatch.model.Order;

/**
 * @author kaihe
 *
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
