package com.demo.batch.springbatch.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.repository.OrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kaihe
 *
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class DataCleaningTasklet implements Tasklet {

  @NonNull
  private OrderRepository orderRepository;
  
  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    log.info("Truncate all old data");
    orderRepository.truncate();
    return RepeatStatus.FINISHED;
  }

}
