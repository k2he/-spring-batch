package com.demo.batch.springbatch.batch;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.demo.batch.springbatch.batch.stage.OrderStageProcessor;
import com.demo.batch.springbatch.dto.OrderList;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.service.OrderService;


/**
 *
 * @author hekai27
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class OrderStageProcessorTests {
  @Mock
  private OrderService orderService;

  private OrderStageProcessor orderStageProcessor;

  @Before
  public void setUp() {
    orderStageProcessor = new OrderStageProcessor(orderService);
  }

  @Test
  public void whenRunItemProcessor_thenReturnProductCatalogExtractStage() throws Exception {
    OrderList.Order order = new OrderList.Order();
    OrderStage orderStage = OrderStage.builder().build();

    // given
    Mockito.when(orderService.convertToOrderStage(order))
        .thenReturn(orderStage);

    // when
    OrderStage result = orderStageProcessor.process(order);

    // then
    assertNotNull(result);
  }
}
