package com.demo.batch.springbatch.batch.order;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.repository.OrderRepository;
import com.demo.batch.springbatch.repository.OrderStageRepository;
import com.demo.batch.springbatch.util.BatchConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@Component
@RequiredArgsConstructor
public class MoveStageToOrderAndBackupTasklet implements Tasklet {

  @NonNull
  private OrderStageRepository orderStageRepository;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    /*
    * Call Store procedure to move data from PROD_CAT_EXTRACT_STAGE table to PROD_CAT_EXTRACT table and
    * then move old data from PROD_CAT_EXTRACT table to PROD_CAT_EXTRACT_HIST table
    */
    orderStageRepository.moveStageToOrderTable();
    return RepeatStatus.FINISHED;
  }


}
