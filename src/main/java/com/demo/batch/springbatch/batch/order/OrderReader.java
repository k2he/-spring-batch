package com.demo.batch.springbatch.batch.order;

import javax.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.model.OrderStage;

/**
 * @author kaihe
 *
 */

@Component
public class OrderReader extends JpaPagingItemReader<OrderStage> {

  private static final String JPQL_QUERY = "SELECT o from OrderStage o";

  @Autowired
  public OrderReader(AppProperties appProperties, EntityManagerFactory entityManagerFactory)
      throws Exception {
    setQueryString(JPQL_QUERY);
    setEntityManagerFactory(entityManagerFactory);
    setPageSize(appProperties.getDatabaseBatchSize());
    afterPropertiesSet();
    setSaveState(true);
    // setSaveState(false); // must be set to false if multi threaded
  }
}
