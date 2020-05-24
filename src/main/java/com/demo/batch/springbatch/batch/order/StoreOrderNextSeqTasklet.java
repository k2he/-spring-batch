package com.demo.batch.springbatch.batch.order;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.repository.OrderRepository;
import com.demo.batch.springbatch.util.BatchConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@Component
@RequiredArgsConstructor
public class StoreOrderNextSeqTasklet implements Tasklet {

  @NonNull
  private OrderRepository orderRepository;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    Long nextSeqValue = orderRepository.getNextSequenceValue();
    ExecutionContext jobExecutionContext =
        contribution.getStepExecution().getJobExecution().getExecutionContext();
    jobExecutionContext.putLong(BatchConstants.ORDER_NEXT_SEQ_VALUE, nextSeqValue);
    return RepeatStatus.FINISHED;
  }


}
