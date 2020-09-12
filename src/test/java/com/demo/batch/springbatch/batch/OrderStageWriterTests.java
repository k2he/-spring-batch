package com.demo.batch.springbatch.batch;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.demo.batch.springbatch.batch.stage.OrderStageWriter;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.repository.OrderStageRepository;


/**
 *
 * @author hekai27
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class OrderStageWriterTests {
  @Mock
  private OrderStageRepository orderStageRepository;

  private OrderStageWriter orderStageWriter;

  @Before
  public void setUp() {
    orderStageWriter = new OrderStageWriter(orderStageRepository);
  }

  @Test
  public void whenRunItemProcessor_thenNoException() throws Exception {
    OrderStage orderStage = OrderStage.builder().build();
    List<OrderStage> items = new ArrayList<OrderStage>();
    items.add(orderStage);

    // given
    Mockito.when(orderStageRepository.saveAll(items)).thenReturn(items);

    // when
    orderStageWriter.write(items);

    // then
    verify(orderStageRepository, times(1)).saveAll(items);
  }
}
