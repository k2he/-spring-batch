package com.demo.batch.springbatch.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.batch.repeat.RepeatStatus;
import com.demo.batch.springbatch.batch.order.MoveStageToOrderAndBackupTasklet;
import com.demo.batch.springbatch.repository.OrderStageRepository;


/**
 *
 * @author kaihe
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MoveStageToOrderAndBackupTaskletTests {
  @Mock
  private OrderStageRepository orderStageRepository;

  private MoveStageToOrderAndBackupTasklet moveStageToOrderAndBackupTasklet;

  @Before
  public void setUp() {
    moveStageToOrderAndBackupTasklet =
        new MoveStageToOrderAndBackupTasklet(orderStageRepository);
  }

  @Test
  public void whenRunTasklet_thenReturnFinished() throws Exception {
    // when
    RepeatStatus result = moveStageToOrderAndBackupTasklet.execute(null, null);

    // then
    assertNotNull(result);
    assertEquals(RepeatStatus.FINISHED, result);

    verify(orderStageRepository, times(1)).moveStageToOrderTable();
  }
}

