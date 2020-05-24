/**
 * 
 */
package com.demo.batch.springbatch.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.demo.batch.springbatch.model.Order;

/**
 * @author kaihe
 *
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query(value = "SELECT Auto_increment FROM information_schema.tables WHERE table_name='orders'", nativeQuery = true)
  public Long getNextSequenceValue();

  @Query("SELECT o FROM Order o WHERE o.id < :id")
  public List<Order> findAllLessThanGivenId(@Param("id") Long id);

  @Query(value = "DELETE FROM Order WHERE id < :id")
  public void deleteAllLessThanGivenId(@Param("id") Long id);
}
